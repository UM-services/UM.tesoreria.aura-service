package um.tesoreria.aura.configuration;

import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.jupiter.api.Test;
import um.tesoreria.aura.hexagonal.cobranza.infrastructure.messaging.event.AuraPaymentProcessedEvent;

import static org.assertj.core.api.Assertions.assertThat;

class KafkaProducerConfigTest {

    @Test
    void serializaElEventoSinTypeId() {
        var config = new KafkaProducerConfig();
        var headers = new RecordHeaders();
        var event = AuraPaymentProcessedEvent.builder().cpe("CPE-1").build();

        byte[] payload = config.kafkaJsonSerializer().serialize("aura-payments-topic", headers, event);

        assertThat(headers.lastHeader("__TypeId__")).isNull();
        assertThat(new String(payload)).contains("\"cpe\":\"CPE-1\"");
    }

}
