from pathlib import Path

from docx import Document as DocxDocument
from pypdf import PdfReader

from app.config.settings import AppSettings


class DocumentLoaderService:
    def __init__(self, settings: AppSettings) -> None:
        self.settings = settings

    def load_directory(self, root: Path) -> list[dict]:
        records: list[dict] = []
        for path in sorted(root.rglob("*")):
            if path.is_file() and path.suffix.lower() in self._allowed_extensions():
                content = self._read_file(path)
                if content.strip():
                    records.append(
                        {
                            "document_id": path.stem,
                            "source": path.name,
                            "path": str(path),
                            "content": content,
                            "metadata": self._infer_metadata(path),
                        }
                    )
        return records

    def _allowed_extensions(self) -> set[str]:
        return {ext.strip().lower() for ext in self.settings.allowed_extensions.split(",") if ext.strip()}

    def _read_file(self, path: Path) -> str:
        suffix = path.suffix.lower()
        if suffix in {".txt", ".md"}:
            return path.read_text(encoding="utf-8")
        if suffix == ".docx":
            doc = DocxDocument(str(path))
            return "\n".join(paragraph.text for paragraph in doc.paragraphs if paragraph.text.strip())
        if suffix == ".pdf":
            reader = PdfReader(str(path))
            text = "\n".join((page.extract_text() or "") for page in reader.pages)
            if text.strip():
                return text
            return self._read_with_unstructured(path)
        raise ValueError(f"Unsupported extension: {suffix}")

    def _read_with_unstructured(self, path: Path) -> str:
        try:
            from unstructured.partition.auto import partition
        except ImportError:
            return ""
        elements = partition(filename=str(path))
        return "\n".join(str(element) for element in elements)

    def _infer_metadata(self, path: Path) -> dict:
        source = path.stem.lower()
        domain = "operations"
        if "snmp" in source:
            domain = "snmp"
        elif "satcom" in source:
            domain = "satcom"
        elif "kubernetes" in source:
            domain = "kubernetes"
        elif "docker" in source:
            domain = "docker"
        elif "rest" in source:
            domain = "rest"
        return {
            "domain": domain,
            "extension": path.suffix.lower(),
            "source_type": "technical_documentation",
            "revision": "v1.0",
            "environment": "noc",
        }
