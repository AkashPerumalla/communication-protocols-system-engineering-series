from fastapi import FastAPI

from app.api.routes import router
from app.config.settings import get_settings
from app.services.container import ServiceContainer


def create_app() -> FastAPI:
    settings = get_settings()
    app = FastAPI(title=settings.app_name, version="1.0.0")
    app.state.container = ServiceContainer(settings)
    app.include_router(router)
    return app


app = create_app()
