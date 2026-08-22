# Changelog

## [0.0.2] - 2026-08-22

### fix
- Corregido nombre de paso en `deploy-develop.yml` y `deploy-staging.yml`: "Deploy tesoreria core service" → "Deploy tesoreria aura service"

### docs
- Añadidos badges de tecnología en README (Java, Spring Boot, Maven, Docker, Kafka, Consul, GitHub Actions, SonarCloud)

## [0.0.1] - 2026-08-22

### feat
- Inicialización del servicio Spring Boot 4.1.0 con Java 25, Consul discovery, Kafka, OpenFeign, actuator y cache Caffeine
- Endpoint `GET /api/tesoreria/aura/hello/` (`HelloTestController`)
- Seguridad Swagger UI con HTTP Basic via `SwaggerSecurityConfig`
- Workflow `deploy-develop.yml`: verify, build Docker y deploy self-hosted en rama `develop`
- Workflow `deploy-staging.yml`: verify, build Docker y deploy self-hosted en rama `staging`
- Workflow `maven.yml`: build + SonarCloud + imagen Docker en rama `main`
- Workflow `generate-docs.yml`: validación Mermaid, generación de wiki y deploy a GitHub Pages
- `Dockerfile` multi-stage con `maven:3-eclipse-temurin-25-alpine` (build) y `eclipse-temurin:25-jre-alpine` (runtime), usuario no privilegiado
