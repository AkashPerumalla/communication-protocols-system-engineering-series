from typing import Any

from src.services.domain_service import DomainService
from src.services.file_service import FileService
from src.services.platform_service import PlatformService
from src.services.postgres_service import PostgresService
from src.services.rest_service import RestService
from src.services.system_service import SystemService
from src.services.tool_registry_service import ToolRegistryService


def register_platform_tools(
    registry: ToolRegistryService,
    system_service: SystemService,
    file_service: FileService,
    rest_service: RestService,
    postgres_service: PostgresService,
    domain_service: DomainService,
    platform_service: PlatformService,
) -> None:
    def system_info(_: dict[str, Any]) -> dict[str, Any]:
        return system_service.system_info()

    def file_read(args: dict[str, Any]) -> dict[str, Any]:
        return file_service.read_file(path=args.get("path", "README.md"))

    def file_search(args: dict[str, Any]) -> dict[str, Any]:
        return file_service.search_file(path=args.get("path", "README.md"), query=args.get("query", "MCP"))

    def directory_listing(args: dict[str, Any]) -> dict[str, Any]:
        return file_service.list_directory(path=args.get("path", "."))

    def rest_api_caller(args: dict[str, Any]) -> dict[str, Any]:
        return rest_service.call(url=args.get("url", "https://example.com"), method=args.get("method", "GET"))

    def postgresql_query(args: dict[str, Any]) -> dict[str, Any]:
        return postgres_service.query(sql=args.get("sql", "SELECT 1 AS ok"))

    def telemetry_lookup(args: dict[str, Any]) -> dict[str, Any]:
        return domain_service.telemetry_lookup(device_id=args.get("device_id"))

    def alarm_lookup(args: dict[str, Any]) -> dict[str, Any]:
        return domain_service.alarm_lookup(severity=args.get("severity"))

    def device_status(args: dict[str, Any]) -> dict[str, Any]:
        return domain_service.device_status(device_id=args.get("device_id"))

    def log_search(args: dict[str, Any]) -> dict[str, Any]:
        return domain_service.log_search(query=args.get("query", ""))

    def platform_status(_: dict[str, Any]) -> dict[str, Any]:
        return platform_service.platform_status()

    registry.register("system_info", "System Info (CPU/RAM/Disk)", system_info)
    registry.register("file_read", "File Read", file_read)
    registry.register("file_search", "File Search", file_search)
    registry.register("directory_listing", "Directory Listing", directory_listing)
    registry.register("rest_api_caller", "REST API Caller", rest_api_caller)
    registry.register("postgresql_query", "PostgreSQL Query", postgresql_query)
    registry.register("telemetry_lookup", "Telemetry Lookup", telemetry_lookup)
    registry.register("alarm_lookup", "Alarm Lookup", alarm_lookup)
    registry.register("device_status", "Device Status", device_status)
    registry.register("log_search", "Log Search", log_search)
    registry.register("platform_status", "Platform Status", platform_status)
