from fastapi import APIRouter, Depends

from src.api.dependencies import get_container
from src.dto.api_dto import AgentChatRequest, AgentRunRequest
from src.models.envelope import ResponseEnvelope, build_envelope
from src.models.markers import AGENT_READY, API_SUCCESS, SYSTEM_HEALTHY, TASK_COMPLETED
from src.services.container import ServiceContainer
from src.utils.time_utils import current_timestamp

router = APIRouter()


@router.get("/health", response_model=ResponseEnvelope)
def health(container: ServiceContainer = Depends(get_container)) -> ResponseEnvelope:
    return build_envelope(True, SYSTEM_HEALTHY, "Platform is healthy", container.platform_service.health(), current_timestamp(container.settings))


@router.get("/platform/status", response_model=ResponseEnvelope)
def platform_status(container: ServiceContainer = Depends(get_container)) -> ResponseEnvelope:
    return build_envelope(True, API_SUCCESS, "Platform status retrieved", container.platform_service.platform_status(), current_timestamp(container.settings))


@router.get("/agent/tools", response_model=ResponseEnvelope)
def list_tools(container: ServiceContainer = Depends(get_container)) -> ResponseEnvelope:
    return build_envelope(True, AGENT_READY, "Agent tools listed", {"tools": container.tool_registry.list_tools()}, current_timestamp(container.settings))


@router.post("/agent/chat", response_model=ResponseEnvelope)
def agent_chat(payload: AgentChatRequest, container: ServiceContainer = Depends(get_container)) -> ResponseEnvelope:
    result = container.agent_orchestrator.run(session_id=payload.session_id, query=payload.query, context=payload.context)
    return build_envelope(True, TASK_COMPLETED, "Agent chat completed", result, current_timestamp(container.settings))


@router.post("/agent/run", response_model=ResponseEnvelope)
def agent_run(payload: AgentRunRequest, container: ServiceContainer = Depends(get_container)) -> ResponseEnvelope:
    result = container.agent_orchestrator.run(session_id=payload.session_id, query=payload.task, context=payload.inputs)
    return build_envelope(True, TASK_COMPLETED, "Agent task executed", result, current_timestamp(container.settings))


@router.get("/agent/history", response_model=ResponseEnvelope)
def agent_history(session_id: str = "default", container: ServiceContainer = Depends(get_container)) -> ResponseEnvelope:
    history = container.agent_orchestrator.history(session_id=session_id)
    return build_envelope(True, API_SUCCESS, "Agent history retrieved", {"session_id": session_id, "messages": history}, current_timestamp(container.settings))
