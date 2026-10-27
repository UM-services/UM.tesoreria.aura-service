package um.tesoreria.aura.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KafkaTopicsConfigurationTest {

    private final KafkaTopicsConfiguration configuration = new KafkaTopicsConfiguration();

    @Test
    void declaraElTopicDePagosDeAura() {
        NewTopic topic = configuration.auraPaymentTopic("aura-payments-topic", 1, 1);

        assertThat(topic.name()).isEqualTo("aura-payments-topic");
        assertThat(topic.numPartitions()).isEqualTo(1);
        assertThat(topic.replicationFactor()).isEqualTo((short) 1);
        assertThat(topic.configs()).containsEntry("min.insync.replicas", "1");
    }

    @Test
    void ajustaMinInsyncReplicasAlReplicationFactor() {
        assertThat(configuration.auraPaymentTopic("aura-payments-topic", 1, 3).configs())
                .containsEntry("min.insync.replicas", "2");
    }
}
