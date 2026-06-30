# syntax=docker/dockerfile:1
FROM python:3.11-slim AS builder
WORKDIR /build
COPY pyproject.toml .
RUN pip install --no-cache-dir --upgrade pip && pip install --no-cache-dir uv

FROM python:3.11-slim AS runtime
WORKDIR /app
ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1
ENV APP_PORT=8094
COPY --from=builder /usr/local /usr/local
COPY . .
RUN uv sync --frozen || uv sync
RUN useradd --system --create-home agentuser && chown -R agentuser:agentuser /app
USER agentuser
EXPOSE 8094
HEALTHCHECK --interval=20s --timeout=5s --start-period=20s --retries=6 CMD wget -qO- http://localhost:8094/health | grep -q 'SYSTEM_HEALTHY' || exit 1
CMD ["uv", "run", "uvicorn", "src.main:app", "--host", "0.0.0.0", "--port", "8094"]
