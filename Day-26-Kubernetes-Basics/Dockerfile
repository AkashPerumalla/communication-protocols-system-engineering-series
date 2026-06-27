# syntax=docker/dockerfile:1

FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Leverage layer caching by resolving dependencies before copying full source.
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests clean package

FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app

RUN apt-get update \
		&& apt-get install -y --no-install-recommends curl \
		&& groupadd --system spring \
		&& useradd --system --gid spring --create-home spring \
		&& rm -rf /var/lib/apt/lists/*

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -Djava.security.egd=file:/dev/./urandom"

COPY --from=builder /workspace/target/app.jar /app/app.jar

RUN chown -R spring:spring /app

USER spring

EXPOSE 8091

HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=5 \
	CMD curl -fsS http://localhost:8091/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
