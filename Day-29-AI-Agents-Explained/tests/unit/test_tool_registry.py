from src.exceptions.custom_exceptions import ToolNotFoundError
from src.tools.tool_registry import ToolRegistry


def test_tool_registry_executes_registered_tool() -> None:
    registry = ToolRegistry()
    registry.register("sample", "sample tool", lambda _: {"ok": True})

    result = registry.execute("sample", {})

    assert result["ok"] is True


def test_tool_registry_raises_for_unknown_tool() -> None:
    registry = ToolRegistry()

    try:
        registry.execute("missing", {})
        assert False
    except ToolNotFoundError as exc:
        assert "Unknown tool" in str(exc)


def test_tool_registry_respects_disabled_tool() -> None:
    registry = ToolRegistry()
    registry.register("sample", "sample tool", lambda _: {"ok": True}, enabled=False)
    result = registry.execute("sample", {})
    assert result["status"] == "degraded"
