from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse
from pydantic import ValidationError as PydanticValidationError

from src.exceptions.custom_exceptions import DatabaseUnavailableError, PlatformError, ToolNotFoundError, ValidationError
from src.models.envelope import build_envelope
from src.models.markers import API_SUCCESS
from src.utils.time_utils import current_timestamp


def register_exception_handlers(app: FastAPI) -> None:
    def _timestamp(request: Request) -> str:
        container = getattr(request.app.state, "container", None)
        if container is None:
            from datetime import datetime, timezone

            return datetime.now(timezone.utc).isoformat()
        return current_timestamp(container.settings)

    @app.exception_handler(ValidationError)
    async def validation_error_handler(request: Request, exc: ValidationError) -> JSONResponse:
        payload = build_envelope(False, API_SUCCESS, str(exc), {}, _timestamp(request))
        return JSONResponse(status_code=400, content=payload.model_dump())

    @app.exception_handler(ToolNotFoundError)
    async def tool_not_found_handler(request: Request, exc: ToolNotFoundError) -> JSONResponse:
        payload = build_envelope(False, API_SUCCESS, str(exc), {}, _timestamp(request))
        return JSONResponse(status_code=404, content=payload.model_dump())

    @app.exception_handler(DatabaseUnavailableError)
    async def database_error_handler(request: Request, exc: DatabaseUnavailableError) -> JSONResponse:
        payload = build_envelope(False, API_SUCCESS, str(exc), {}, _timestamp(request))
        return JSONResponse(status_code=503, content=payload.model_dump())

    @app.exception_handler(PydanticValidationError)
    async def pydantic_error_handler(request: Request, exc: PydanticValidationError) -> JSONResponse:
        payload = build_envelope(False, API_SUCCESS, "Request validation failed", {"details": exc.errors()}, _timestamp(request))
        return JSONResponse(status_code=422, content=payload.model_dump())

    @app.exception_handler(PlatformError)
    async def platform_error_handler(request: Request, exc: PlatformError) -> JSONResponse:
        payload = build_envelope(False, API_SUCCESS, str(exc), {}, _timestamp(request))
        return JSONResponse(status_code=500, content=payload.model_dump())

    @app.exception_handler(Exception)
    async def generic_error_handler(request: Request, exc: Exception) -> JSONResponse:
        payload = build_envelope(False, API_SUCCESS, "Unexpected error", {"type": type(exc).__name__}, _timestamp(request))
        return JSONResponse(status_code=500, content=payload.model_dump())
