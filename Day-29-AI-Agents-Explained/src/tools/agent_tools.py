from typing import Any

from src.services.domain_service import DomainService
from src.services.file_service import FileService
from src.services.platform_service import PlatformService
from src.services.postgres_service import PostgresService
from src.services.rest_service import RestService
from src.services.system_service import SystemService
from src.tools.tool_registry import ToolRegistry


def register_agent_tools(
    registry: ToolRegistry,
    system_service: SystemService,
    file_service: FileService,
    rest_service: RestService,
    postgres_service: PostgresService,
    domain_service: DomainService,
    platform_service: PlatformService,
) -> None:
    def system_info(_: dict[str, Any]) -> dict[str, Any]:
        return system_service.system_info()

    def file_search_read(args: dict[str, Any]) -> dict[str, Any]:
        path = str(args.get("path", "README.md"))
        query = str(args.get("query", "Agent"))
        read_payload = file_service.read_file(path)
        search_payload = file_service.search_file(path=path, query=query)
        return {
            "path": read_payload["path"],
            "matches": search_payload["matches"],
            "content_preview": read_payload["content"][:500],
        }

    def rest_api_client(args: dict[str, Any]) -> dict[str, Any]:
        return rest_service.call(
            url=str(args.get("url", "https://example.com")),
            method=str(args.get("method", "GET")),
        )

    def postgresql_query(args: dict[str, Any]) -> dict[str, Any]:
        return postgres_service.query(str(args.get("sql", "SELECT 1 AS ok")))

    def device_status(args: dict[str, Any]) -> dict[str, Any]:
        return domain_service.device_status(device_id=args.get("device_id"))

    def telemetry_lookup(args: dict[str, Any]) -> dict[str, Any]:
        return domain_service.telemetry_lookup(device_id=args.get("device_id"))

    def alarm_lookup(args: dict[str, Any]) -> dict[str, Any]:
        return domain_service.alarm_lookup(severity=args.get("severity"))

    def log_search(args: dict[str, Any]) -> dict[str, Any]:
        return domain_service.log_search(query=str(args.get("query", "")))

    def knowledge_search(args: dict[str, Any]) -> dict[str, Any]:
        query = str(args.get("query", ""))
        telemetry = domain_service.telemetry_lookup().get("items", [])
        alarms = domain_service.alarm_lookup().get("items", [])
        logs = domain_service.log_search(query=query or "").get("items", [])
        knowledge_hits = []
        for item in telemetry:
            if query.lower() in str(item).lower():
                knowledge_hits.append({"source": "telemetry", "record": item})
        for item in alarms:
            if query.lower() in str(item).lower():
                knowledge_hits.append({"source": "alarms", "record": item})
        for item in logs:
            knowledge_hits.append({"source": "logs", "record": item})
        return {"query": query, "count": len(knowledge_hits), "items": knowledge_hits}

    def platform_status(_: dict[str, Any]) -> dict[str, Any]:
        return platform_service.platform_status()

    registry.register("system_info", "System host metrics", system_info)
    registry.register("file_search_read", "Read file content and locate matching lines", file_search_read)
    registry.register("rest_api_client", "Invoke external REST endpoint", rest_api_client)
    registry.register("postgresql_query", "Run read-only SQL query", postgresql_query)
    registry.register("device_status", "Inspect NOC device status", device_status)
    registry.register("telemetry_lookup", "Lookup telemetry data", telemetry_lookup)
    registry.register("alarm_lookup", "Lookup alarms by severity", alarm_lookup)
    registry.register("log_search", "Search platform logs", log_search)
    registry.register("knowledge_search", "Search across telemetry, alarms, and logs", knowledge_search)
    registry.register("platform_status", "Summarize platform status", platform_status)
