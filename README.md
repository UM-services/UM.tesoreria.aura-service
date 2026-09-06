# UM.tesoreria.aura-service

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-green)
![Maven](https://img.shields.io/badge/Maven-build-blue)
![Docker](https://img.shields.io/badge/Docker-multi--stage-blue)
![Kafka](https://img.shields.io/badge/Kafka-producer-black)
![Consul](https://img.shields.io/badge/Consul-service%20discovery-pink)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-CI/CD-blueviolet)
![SonarCloud](https://img.shields.io/badge/SonarCloud-quality%20gate-yellow)

**Versión:** 0.1.0

Servicio Spring Boot 4.1.1 (Java 25) del módulo Aura de Tesoreria UM.

## Características

- Registro en Consul con service discovery
- Comunicación entre servicios via OpenFeign
- Generación de checkout links contra la Aura GIRE API (módulo **checkout**)
- Polling programado de cobranzas de Aura y publicación de eventos de pago a Kafka (módulo **cobranza**)
- Productor Kafka (`AuraPaymentProcessedEvent`, topic `aura-payments-topic`)
- Swagger UI protegido con HTTP Basic
- Actuator (health, scheduledtasks, env)
- Cache Caffeine
- Imagen Docker multi-stage (JRE 25 Alpine)

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/tesoreria/aura/hello/` | Hello World |
| POST | `/api/tesoreria/aura/checkout/generate` | Generar checkout link individual |
| POST | `/api/tesoreria/aura/checkout/generate-bulk` | Generar checkout links masivamente |

## Arquitectura

Los módulos **checkout** y **cobranza** siguen el patrón de **Arquitectura Hexagonal** (Ports & Adapters):

| Capa | Responsabilidad |
|------|----------------|
| **Domain** | Modelos (`CheckoutLink`, `Cobranza`, `AuraConvenio`) y puertos (`GenerateCheckoutLinkUseCase`, `PollCobranzasUseCase`, `AuraCobranzasPort`) |
| **Application** | Servicios (`CheckoutService`, `CobranzasService`) e implementación de casos de uso (`GenerateCheckoutLinkUseCaseImpl`, `PollCobranzasUseCaseImpl`) |
| **Infrastructure - Web** | Controller REST, DTOs de entrada/salida y mapper |
| **Infrastructure - Client / Core** | Adapter hacia Aura GIRE API y tesoreria-core via OpenFeign, servicio de tokens OAuth2 |
| **Infrastructure - Scheduled / Messaging** | Driver adapter `AuraCobranzasScheduled` (crons de polling) y producer `AuraPaymentKafkaProducer` |

El módulo **cobranza** no expone endpoints: se dispara por scheduler (`APP_AURA_SCHEDULER_CRON`, cada 15 min para el día en curso; `APP_AURA_SCHEDULER_CRON_NIGHTLY`, 02:00 para el día anterior), consulta los convenios activos en tesoreria-core, obtiene las cobranzas de la Aura GIRE API y publica cada pago como `AuraPaymentProcessedEvent` en Kafka (`APP_AURA_KAFKA_TOPIC`).

### Diagramas

Los diagramas Mermaid se encuentran en [`docs/`](docs/) y son validados automáticamente por el workflow `generate-docs.yml`:

| Diagrama | Archivo | Descripción |
|----------|---------|-------------|
| Arquitectura Hexagonal | [`hexagonal-architecture.mmd`](docs/hexagonal-architecture.mmd) | Capas y dependencias del módulo checkout |
| Secuencia de Checkout | [`checkout-sequence.mmd`](docs/checkout-sequence.mmd) | Flujo de generación de checkout link |
| Secuencia de Cobranzas | [`cobranza-sequence.mmd`](docs/cobranza-sequence.mmd) | Flujo de polling de cobranzas y publicación Kafka |
| Diagrama de Clases | [`class-diagram.mmd`](docs/class-diagram.mmd) | Modelos, puertos, adaptadores y DTOs |
| Contexto del Sistema | [`system-context.mmd`](docs/system-context.mmd) | Servicio Aura y sistemas externos |

## Configuración

Puerto por defecto: `8098` (variable `APP_PORT`).

Variables de entorno principales:

| Variable | Default | Descripción |
|----------|---------|-------------|
| `APP_PORT` | `8098` | Puerto del servicio |
| `APP_SWAGGER_USERNAME` | `tesoreria` | Usuario Swagger UI |
| `APP_SWAGGER_PASSWORD` | `tesoreria` | Contraseña Swagger UI |
| `APP_AURA_SCHEDULER_CRON` | `0 */15 * * * *` | Cron polling cobranzas online (día en curso) |
| `APP_AURA_SCHEDULER_CRON_NIGHTLY` | `0 0 2 * * *` | Cron polling cobranzas batch (día anterior) |
| `APP_AURA_KAFKA_TOPIC` | `aura-payments-topic` | Topic Kafka de eventos de pago |

## CI/CD

| Workflow | Trigger | Descripción |
|----------|---------|-------------|
| `maven.yml` | push/PR a `main` | Build + SonarCloud + imagen Docker |
| `deploy-develop.yml` | push/PR a `develop` | Deploy a entorno develop |
| `deploy-staging.yml` | push/PR a `staging` | Deploy a entorno staging |
| `generate-docs.yml` | PRs + push a `main` | Documentación + GitHub Pages |
