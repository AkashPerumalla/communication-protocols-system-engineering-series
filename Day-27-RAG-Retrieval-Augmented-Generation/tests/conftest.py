from pathlib import Path
import sys

import pytest

ROOT = Path(__file__).resolve().parents[1]
if str(ROOT) not in sys.path:
    sys.path.insert(0, str(ROOT))

from app.config.settings import AppSettings
from app.services.sample_documents_service import SampleDocumentsService


@pytest.fixture
def test_settings(tmp_path: Path) -> AppSettings:
    docs_dir = tmp_path / "documents"
    chroma_dir = tmp_path / "chromadb"
    settings = AppSettings(
        documents_dir=docs_dir,
        chromadb_dir=chroma_dir,
        use_mock_llm=True,
        use_mock_embeddings=True,
        collection_name="test_collection",
    )
    SampleDocumentsService().ensure_seed_documents(docs_dir)
    return settings
