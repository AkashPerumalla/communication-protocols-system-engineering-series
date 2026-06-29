import pytest

from src.exceptions.custom_exceptions import ValidationError
from src.services.container import ServiceContainer


def test_domain_data_loaded(container: ServiceContainer) -> None:
    telemetry = container.domain_service.telemetry_lookup()
    alarms = container.domain_service.alarm_lookup()
    devices = container.domain_service.device_status()

    assert telemetry["count"] >= 1
    assert alarms["count"] >= 1
    assert devices["count"] >= 1


def test_postgres_fallback(container: ServiceContainer) -> None:
    result = container.postgres_service.query("SELECT 1")
    assert result["mode"] == "mock"
    assert result["row_count"] == 1


def test_postgres_rejects_non_read_only_sql(container: ServiceContainer) -> None:
    with pytest.raises(ValidationError):
        container.postgres_service.query("DELETE FROM telemetry")
