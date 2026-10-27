# Changelog

## [0.2.0] - 2026-10-27

### feat
- `KafkaTopicsConfiguration` declara `aura-payments-topic` como bean `NewTopic`; Spring Boot lo crea al arrancar mediante el `KafkaAdmin` auto-configurado (el servicio es dueño del topic porque solo lo produce)
- Particiones y replication factor del topic son configurables por entorno y `min.insync.replicas` se ajusta a `min(2, réplicas)`, de modo que un broker único sigue siendo válido

### changed
- `bootstrap.yml`: nuevas variables `APP_KAFKA_TOPIC_PARTITIONS` y `APP_KAFKA_TOPIC_REPLICAS` (default `1`) bajo `app.kafka.topics`

### test
- `KafkaTopicsConfigurationTest` verifica nombre, particiones, replication factor y `min.insync.replicas` con 1 y 3 réplicas

### docs
- README y `generate-docs.yml` sincronizados con la versión 0.2.0; README documenta la creación automática del topic y las nuevas variables de entorno

## [0.1.4] - 2026-10-23

### feat
- `KafkaProducerConfig` define explícitamente `ProducerFactory` y `KafkaTemplate` de `aura-payments-topic` con `StringSerializer` para la clave y `JacksonJsonSerializer` sin type info para el valor; `KafkaProducerConfigTest` verifica que el evento se serializa como JSON sin cabecera `__TypeId__`

### changed
- `aura-payments-topic` deja de emitir `__TypeId__`: el contrato es el JSON del evento. Se eliminaron los serializers de `spring.kafka.producer` del `bootstrap.yml` (los define la configuración explícita) y el `KafkaTestConfig` mock de tests, que ya no era necesario
- `bootstrap.yml`: Consul discovery con `query-passing: true` para resolver solo instancias con los checks en passing (Feign/LoadBalancer)

### deps
- Spring Boot parent: 4.1.1 → 4.1.2
- Spring Cloud: 2025.1.3 → 2025.1.4
- springdoc-openapi-starter-webmvc-ui: 3.0.3 → 3.1.1
- jacoco-maven-plugin: 0.8.13 → 0.8.15

### docs
- `generate-docs.yml`: la versión y la versión de Spring Boot mostradas en la página de GitHub Pages generada se sincronizan con el servicio (0.1.3 → 0.1.4, Spring Boot 4.1.1 → 4.1.2)

## [0.1.3] - 2026-10-21

### fix
- `bootstrap.yml`: el health check de Consul ahora consulta `/actuator/health/readiness` (`health-check-path`) y poda la instancia tras 5 minutos en estado crítico (`health-check-critical-timeout: 5m`)

### docs
- `generate-docs.yml`: la versión mostrada en la página de GitHub Pages generada se sincroniza con la del servicio (`0.1.2` → `0.1.3`)

## [0.1.2] - 2026-10-18

### ci
- `deploy-staging.yml`: el job `deploy` usa una matriz de entornos (`staging`, `staging-new`) con `fail-fast: false`; cada ejecución corre en el runner self-hosted del entorno (`[self-hosted, linux, <entorno>]`) y el nombre del job lo refleja (`Deploy staging service (<entorno>)`)

### fix
- `generate-docs.yml`: corregido el nombre del proyecto en la wiki generada ("UM.tesoreria.core-service" → "UM.tesoreria.aura-service")

### docs
- `generate-docs.yml`: la versión mostrada en la página de GitHub Pages generada se sincroniza con la del servicio (`0.1.0` → `0.1.2`)

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
