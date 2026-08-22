# UM.tesoreria.aura-service

**Versión:** 0.0.1

Servicio Spring Boot 4.1.0 (Java 25) del módulo Aura de Tesoreria UM.

## Características

- Registro en Consul con service discovery
- Comunicación entre servicios via OpenFeign
- Productor Kafka
- Swagger UI protegido con HTTP Basic
- Actuator (health, scheduledtasks, env)
- Cache Caffeine
- Imagen Docker multi-stage (JRE 25 Alpine)

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/tesoreria/aura/hello/` | Hello World |

## Configuración

Puerto por defecto: `8098` (variable `APP_PORT`).

Variables de entorno principales:

| Variable | Default | Descripción |
|----------|---------|-------------|
| `APP_PORT` | `8098` | Puerto del servicio |
| `APP_SWAGGER_USERNAME` | `tesoreria` | Usuario Swagger UI |
| `APP_SWAGGER_PASSWORD` | `tesoreria` | Contraseña Swagger UI |

## CI/CD

| Workflow | Trigger | Descripción |
|----------|---------|-------------|
| `maven.yml` | push/PR a `main` | Build + SonarCloud + imagen Docker |
| `deploy-develop.yml` | push/PR a `develop` | Deploy a entorno develop |
| `deploy-staging.yml` | push/PR a `staging` | Deploy a entorno staging |
| `generate-docs.yml` | PRs + push a `main` | Documentación + GitHub Pages |
