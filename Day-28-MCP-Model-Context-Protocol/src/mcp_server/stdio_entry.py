from src.mcp_server.server import MCPServer
from src.services.container import ServiceContainer


def main() -> None:
    server = MCPServer(ServiceContainer())
    server.run_stdio()


if __name__ == "__main__":
    main()
