package um.tesoreria.aura.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Topic del que {@code tesoreria-aura} es dueño: {@code aura-payments-topic}, que produce este
 * servicio y consume el integrador externo (Aura/GIRE). No tiene consumidor dentro del workspace,
 * así que el dueño es el productor.
 *
 * <p>Spring Boot lo crea al arrancar con el {@code KafkaAdmin} auto-configurado. El réplication
 * factor es ambiental ({@code app.kafka.topics.replicas}); uno por defecto mientras el broker sea
 * único.</p>
 */
@Configuration
public class KafkaTopicsConfiguration {

    @Bean
    NewTopic auraPaymentTopic(@Value("${app.aura.kafka.topic:aura-payments-topic}") String topic,
                              @Value("${app.kafka.topics.partitions:1}") int partitions,
                              @Value("${app.kafka.topics.replicas:1}") int replicas) {
        return TopicBuilder.name(topic)
                .partitions(partitions)
                .replicas(replicas)
                .config("min.insync.replicas", String.valueOf(Math.min(2, replicas)))
                .build();
    }
}
