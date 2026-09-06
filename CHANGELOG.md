# Changelog

## [0.1.1] - 2026-09-06

### deps
- Dependencia Kafka migrada de `org.springframework.kafka:spring-kafka` a `org.springframework.boot:spring-boot-starter-kafka` (4.1.1), según la convención de starters de Spring Boot 4; `spring-kafka` se sigue resolviendo de forma transitiva vía `spring-boot-kafka`

## [0.1.0] - 2026-09-06

### feat
- Nuevo módulo **cobranza** con arquitectura hexagonal: polling automático de cobranzas (pagos) de Aura
- `AuraCobranzasScheduled`: polling online cada 15 minutos del día en curso (`0 */15 * * * *`) y nightly a las 02:00 del día anterior para canales batch (`0 0 2 * * *`)
- `PollCobranzasUseCaseImpl`: obtiene convenios activos de tesoreria-core, consulta cobranzas por convenio en la Aura GIRE API y publica los pagos
- Adapter `AuraCoreConvenioAdapter` via OpenFeign contra `tesoreria-core-service` (`GET /api/tesoreria/core/aura/convenio/active`)
- Adapter `AuraCobranzasAdapter` via OpenFeign (`GET /aura-api/v0/cobranzas`) reutilizando el token OAuth2 de `AuraTokenService`
- `AuraPaymentKafkaProducer`: publica `AuraPaymentProcessedEvent` en Kafka con clave `cpe_debtId` (topic default `aura-payments-topic`)
- Nuevas variables de entorno en `bootstrap.yml`: `APP_AURA_SCHEDULER_CRON`, `APP_AURA_SCHEDULER_CRON_NIGHTLY`, `APP_AURA_KAFKA_TOPIC`

### deps
- Spring Boot parent: 4.1.0 → 4.1.1
- Spring Cloud: 2025.1.2 → 2025.1.3

### ci
- `generate-docs.yml`: los SVG generados se copian a `docs/` y se genera `index.html` para GitHub Pages

### test
- `KafkaTestConfig` con `KafkaTemplate` mockeado para el contexto de pruebas

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
