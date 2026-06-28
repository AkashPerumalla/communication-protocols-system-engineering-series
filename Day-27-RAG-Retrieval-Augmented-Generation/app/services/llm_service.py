from pathlib import Path

from openai import OpenAI

from app.config.settings import AppSettings


class LLMService:
    def __init__(self, settings: AppSettings) -> None:
        self.settings = settings
        self._prompt_template = Path("app/prompts/rag_prompt.txt").read_text(encoding="utf-8")

    def generate_answer(self, question: str, retrieved_context: list[dict]) -> str:
        if self.settings.use_mock_llm or not self.settings.openai_api_key:
            return self._mock_answer(question, retrieved_context)
        return self._openai_answer(question, retrieved_context)

    def _mock_answer(self, question: str, retrieved_context: list[dict]) -> str:
        if not retrieved_context:
            return "No grounded evidence was retrieved from the indexed knowledge base."
        top_sources = ", ".join({item.get("source", "unknown") for item in retrieved_context[:3]})
        highlights = " ".join(item.get("text", "")[:220] for item in retrieved_context[:2]).strip()
        return (
            f"Question: {question}\n"
            f"Grounded summary: {highlights}\n"
            f"Operational note: Validate against source documents: {top_sources}."
        )

    def _openai_answer(self, question: str, retrieved_context: list[dict]) -> str:
        context_text = "\n\n".join(
            [f"Source: {item.get('source', 'unknown')}\n{item.get('text', '')}" for item in retrieved_context]
        )
        prompt = self._prompt_template.format(question=question, context=context_text)
        client = OpenAI(api_key=self.settings.openai_api_key)
        response = client.chat.completions.create(
            model=self.settings.openai_model,
            temperature=0.1,
            messages=[
                {"role": "system", "content": "You are a factual operations assistant."},
                {"role": "user", "content": prompt},
            ],
        )
        return response.choices[0].message.content or "No response generated."
