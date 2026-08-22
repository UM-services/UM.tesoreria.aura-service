# UM.tesoreria.aura-service

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-green)
![Maven](https://img.shields.io/badge/Maven-build-blue)
![Docker](https://img.shields.io/badge/Docker-multi--stage-blue)
![Kafka](https://img.shields.io/badge/Kafka-producer-black)
![Consul](https://img.shields.io/badge/Consul-service%20discovery-pink)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-CI/CD-blueviolet)
![SonarCloud](https://img.shields.io/badge/SonarCloud-quality%20gate-yellow)

**Versión:** 0.0.2

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
