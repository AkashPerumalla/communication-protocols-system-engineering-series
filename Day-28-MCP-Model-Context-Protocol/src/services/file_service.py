from pathlib import Path

from src.exceptions.custom_exceptions import ValidationError
from src.utils.file_utils import ensure_safe_path


class FileService:
    def __init__(self, base_dir: Path) -> None:
        self._base_dir = base_dir

    def read_file(self, path: str) -> dict[str, str]:
        file_path = ensure_safe_path(self._base_dir, path)
        if not file_path.exists() or not file_path.is_file():
            raise ValidationError("File does not exist")
        return {"path": str(file_path.relative_to(self._base_dir)), "content": file_path.read_text(encoding="utf-8")}

    def search_file(self, path: str, query: str) -> dict[str, object]:
        if not query:
            raise ValidationError("Query cannot be empty")
        payload = self.read_file(path)
        lines = payload["content"].splitlines()
        matches = []
        for idx, line in enumerate(lines, start=1):
            if query.lower() in line.lower():
                matches.append({"line": idx, "content": line})
        return {"path": payload["path"], "query": query, "matches": matches}

    def list_directory(self, path: str = ".") -> dict[str, object]:
        dir_path = ensure_safe_path(self._base_dir, path)
        if not dir_path.exists() or not dir_path.is_dir():
            raise ValidationError("Directory does not exist")
        entries = sorted([entry.name + ("/" if entry.is_dir() else "") for entry in dir_path.iterdir()])
        return {"path": str(dir_path.relative_to(self._base_dir)), "entries": entries}
