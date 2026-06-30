from fastapi import Request

from src.services.container import ServiceContainer


def get_container(request: Request) -> ServiceContainer:
    container = getattr(request.app.state, "container", None)
    if isinstance(container, ServiceContainer):
        return container
    request.app.state.container = ServiceContainer()
    return request.app.state.container
