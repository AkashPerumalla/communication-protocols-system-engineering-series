from src.exceptions.custom_exceptions import ToolNotFoundError
from src.services.tool_registry_service import ToolRegistryService


def test_tool_registry_executes_registered_tool() -> None:
    registry = ToolRegistryService()
    registry.register("sample", "sample tool", lambda _: {"ok": True})

    result = registry.execute("sample", {})

    assert result["ok"] is True


def test_tool_registry_raises_for_unknown_tool() -> None:
    registry = ToolRegistryService()

    try:
        registry.execute("missing", {})
        assert False
    except ToolNotFoundError as exc:
        assert "Unknown tool" in str(exc)
