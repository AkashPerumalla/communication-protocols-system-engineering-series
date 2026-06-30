from pathlib import Path

from src.agent.orchestrator import AgentOrchestrator
from src.config.settings import AppSettings, get_settings
from src.memory.conversation_store import ConversationStore
from src.planner.planner_service import PlannerService
from src.services.data_repository import DataRepository
from src.services.domain_service import DomainService
from src.services.file_service import FileService
from src.services.llm_service import MockLLMService
from src.services.platform_service import PlatformService
from src.services.postgres_service import PostgresService
from src.services.rest_service import RestService
from src.services.system_service import SystemService
from src.tools.agent_tools import register_agent_tools
from src.tools.tool_registry import ToolRegistry


class ServiceContainer:
    def __init__(self, settings: AppSettings | None = None) -> None:
        self.settings = settings or get_settings()
        self.repository = DataRepository(Path(self.settings.sample_data_file))
        self.system_service = SystemService(self.settings)
        self.file_service = FileService(Path("."))
        self.rest_service = RestService(self.settings)
        self.postgres_service = PostgresService(self.settings)
        self.domain_service = DomainService(self.repository)
        self.platform_service = PlatformService(self.system_service, self.postgres_service)
        self.tool_registry = ToolRegistry()

        register_agent_tools(
            registry=self.tool_registry,
            system_service=self.system_service,
            file_service=self.file_service,
            rest_service=self.rest_service,
            postgres_service=self.postgres_service,
            domain_service=self.domain_service,
            platform_service=self.platform_service,
        )

        self.memory_store = ConversationStore(self.settings)
        self.planner_service = PlannerService()
        self.llm_service = MockLLMService(self.settings)
        self.agent_orchestrator = AgentOrchestrator(
            planner=self.planner_service,
            tool_registry=self.tool_registry,
            memory_store=self.memory_store,
            llm_service=self.llm_service,
        )
