class PlatformError(Exception):
    """Base exception for platform failures."""


class ValidationError(PlatformError):
    """Raised when input validation fails in services."""


class ToolNotFoundError(PlatformError):
    """Raised when a tool name is unknown."""


class DatabaseUnavailableError(PlatformError):
    """Raised when database operation is requested but not available."""
