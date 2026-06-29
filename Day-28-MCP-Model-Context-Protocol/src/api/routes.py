from fastapi import APIRouter, Depends

from src.api.dependencies import get_container
from src.dto.api_dto import QueryRequest, ToolInvocationRequest
from src.models.envelope import ResponseEnvelope, build_envelope
from src.models.markers import API_SUCCESS, QUERY_COMPLETED, SYSTEM_HEALTHY, TOOL_EXECUTED
from src.services.container import ServiceContainer
from src.utils.time_utils import current_timestamp

router = APIRouter()


@router.get("/health", response_model=ResponseEnvelope)
def health(container: ServiceContainer = Depends(get_container)) -> ResponseEnvelope:
    return build_envelope(True, SYSTEM_HEALTHY, "Platform is healthy", container.platform_service.health(), current_timestamp(container.settings))


@router.get("/platform/status", response_model=ResponseEnvelope)
def platform_status(container: ServiceContainer = Depends(get_container)) -> ResponseEnvelope:
    return build_envelope(True, API_SUCCESS, "Platform status retrieved", container.platform_service.platform_status(), current_timestamp(container.settings))


@router.get("/tools", response_model=ResponseEnvelope)
def list_tools(container: ServiceContainer = Depends(get_container)) -> ResponseEnvelope:
    return build_envelope(True, API_SUCCESS, "Tools listed", {"tools": container.tool_registry.list_tools()}, current_timestamp(container.settings))


@router.post("/query", response_model=ResponseEnvelope)
def query(payload: QueryRequest, container: ServiceContainer = Depends(get_container)) -> ResponseEnvelope:
    result = container.query_service.execute_query(payload.query, payload.filters)
    return build_envelope(True, QUERY_COMPLETED, "Query executed", result, current_timestamp(container.settings))


@router.post("/tool/{name}", response_model=ResponseEnvelope)
def execute_tool(name: str, payload: ToolInvocationRequest, container: ServiceContainer = Depends(get_container)) -> ResponseEnvelope:
    result = container.tool_registry.execute(name, payload.arguments)
    return build_envelope(True, TOOL_EXECUTED, "Tool executed", {"tool": name, "result": result}, current_timestamp(container.settings))
