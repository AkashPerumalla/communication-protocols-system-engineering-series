from fastapi import FastAPI

from src.api.exception_handlers import register_exception_handlers
from src.api.routes import router
from src.config.logging_config import configure_logging
from src.config.settings import get_settings
from src.services.container import ServiceContainer


def create_app() -> FastAPI:
    settings = get_settings()
    configure_logging(settings)

    app = FastAPI(title=settings.app_name)
    app.state.container = ServiceContainer(settings)
    app.include_router(router)
    register_exception_handlers(app)
    return app


app = create_app()
