from pathlib import Path

from src.config.settings import AppSettings, get_settings
from src.services.data_repository import DataRepository
from src.services.domain_service import DomainService
from src.services.file_service import FileService
from src.services.platform_service import PlatformService
from src.services.postgres_service import PostgresService
from src.services.query_service import QueryService
from src.services.rest_service import RestService
from src.services.system_service import SystemService
from src.services.tool_registry_service import ToolRegistryService
from src.tools.platform_tools import register_platform_tools


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
        self.tool_registry = ToolRegistryService()

        register_platform_tools(
            registry=self.tool_registry,
            system_service=self.system_service,
            file_service=self.file_service,
            rest_service=self.rest_service,
            postgres_service=self.postgres_service,
            domain_service=self.domain_service,
            platform_service=self.platform_service,
        )

        self.query_service = QueryService(self.tool_registry)
