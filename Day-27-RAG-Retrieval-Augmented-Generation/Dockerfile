# syntax=docker/dockerfile:1
FROM python:3.11-slim AS builder
WORKDIR /build
COPY requirements.txt .
RUN pip install --no-cache-dir --upgrade pip && pip install --no-cache-dir -r requirements.txt

FROM python:3.11-slim AS runtime
WORKDIR /app
ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1
ENV APP_PORT=8092
COPY --from=builder /usr/local /usr/local
COPY . .
RUN useradd --system --create-home raguser && chown -R raguser:raguser /app
USER raguser
EXPOSE 8092
VOLUME ["/app/data/chromadb"]
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=5 CMD wget -qO- http://localhost:8092/actuator/health | grep -q 'RAG_READY' || exit 1
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8092"]
