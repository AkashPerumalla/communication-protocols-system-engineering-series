from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse
from pydantic import ValidationError as PydanticValidationError

from src.api.dependencies import get_container
from src.exceptions.custom_exceptions import DatabaseUnavailableError, PlatformError, ToolNotFoundError, ValidationError
from src.models.envelope import build_envelope
from src.models.markers import API_SUCCESS
from src.utils.time_utils import current_timestamp


def register_exception_handlers(app: FastAPI) -> None:
    @app.exception_handler(ValidationError)
    async def validation_error_handler(_: Request, exc: ValidationError) -> JSONResponse:
        settings = get_container().settings
        payload = build_envelope(False, API_SUCCESS, str(exc), {}, current_timestamp(settings))
        return JSONResponse(status_code=400, content=payload.model_dump())

    @app.exception_handler(ToolNotFoundError)
    async def tool_not_found_handler(_: Request, exc: ToolNotFoundError) -> JSONResponse:
        settings = get_container().settings
        payload = build_envelope(False, API_SUCCESS, str(exc), {}, current_timestamp(settings))
        return JSONResponse(status_code=404, content=payload.model_dump())

    @app.exception_handler(DatabaseUnavailableError)
    async def database_error_handler(_: Request, exc: DatabaseUnavailableError) -> JSONResponse:
        settings = get_container().settings
        payload = build_envelope(False, API_SUCCESS, str(exc), {}, current_timestamp(settings))
        return JSONResponse(status_code=503, content=payload.model_dump())

    @app.exception_handler(PydanticValidationError)
    async def pydantic_error_handler(_: Request, exc: PydanticValidationError) -> JSONResponse:
        settings = get_container().settings
        payload = build_envelope(False, API_SUCCESS, "Request validation failed", {"details": exc.errors()}, current_timestamp(settings))
        return JSONResponse(status_code=422, content=payload.model_dump())

    @app.exception_handler(PlatformError)
    async def platform_error_handler(_: Request, exc: PlatformError) -> JSONResponse:
        settings = get_container().settings
        payload = build_envelope(False, API_SUCCESS, str(exc), {}, current_timestamp(settings))
        return JSONResponse(status_code=500, content=payload.model_dump())

    @app.exception_handler(Exception)
    async def generic_error_handler(_: Request, exc: Exception) -> JSONResponse:
        settings = get_container().settings
        payload = build_envelope(False, API_SUCCESS, "Unexpected error", {"type": type(exc).__name__}, current_timestamp(settings))
        return JSONResponse(status_code=500, content=payload.model_dump())
