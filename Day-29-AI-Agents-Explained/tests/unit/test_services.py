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


def test_planner_selects_alarm_lookup(container: ServiceContainer) -> None:
    plan = container.planner_service.create_plan("show active alarm summary", {})
    assert plan.selected_tool == "alarm_lookup"
    assert "PLAN_CREATED" in plan.trace


def test_orchestrator_workflow_trace_and_memory(container: ServiceContainer) -> None:
    result = container.agent_orchestrator.run("session-1", "show platform status", {})
    assert result["tool"]["name"] == "platform_status"
    assert "TASK_COMPLETED" in result["trace"]
    history = container.agent_orchestrator.history("session-1")
    assert len(history) == 2
