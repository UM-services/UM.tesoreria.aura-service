package um.tesoreria.aura;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.cloud.consul.enabled=false",
        "spring.cloud.consul.discovery.enabled=false",
        "spring.cloud.consul.config.enabled=false",
        // En Boot 4 la auto-configuración vive en spring-boot-kafka y la clase cambió de paquete.
        // El nombre viejo (org.springframework.boot.autoconfigure.kafka...) se ignora en silencio.
        "spring.autoconfigure.exclude=org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration"
})
class AuraApplicationTests {

    @Test
    void contextLoads() {
    }

}
