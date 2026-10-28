package um.tesoreria.aura.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Productor de {@code aura-payments-topic}. El contrato del topic es el JSON del evento, no el
 * FQCN de la clase Java: no se escribe {@code __TypeId__}.
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public JacksonJsonSerializer<Object> kafkaJsonSerializer() {
        JacksonJsonSerializer<Object> serializer = new JacksonJsonSerializer<>();
        serializer.setAddTypeInfo(false);
        return serializer;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory(JacksonJsonSerializer<Object> kafkaJsonSerializer) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Sin KEY/VALUE_SERIALIZER_CLASS: se pasan las instancias al factory.
        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), kafkaJsonSerializer);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
