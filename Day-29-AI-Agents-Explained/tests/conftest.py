from pathlib import Path

import pytest

from src.config.settings import AppSettings
from src.main import create_app
from src.services.container import ServiceContainer


@pytest.fixture
def test_settings() -> AppSettings:
    return AppSettings(
        deterministic_mode=True,
        fixed_timestamp="2026-06-30T00:00:00+00:00",
        sample_data_file=Path("data/samples/platform_data.json"),
        postgres_enabled=False,
        app_port=8094,
    )


@pytest.fixture
def container(test_settings: AppSettings) -> ServiceContainer:
    return ServiceContainer(test_settings)


@pytest.fixture
def app(test_settings: AppSettings):
    app = create_app()
    app.state.container = ServiceContainer(test_settings)
    return app
