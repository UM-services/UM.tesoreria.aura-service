package um.tesoreria.aura.hexagonal.cobranza.infrastructure.messaging.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import um.tesoreria.aura.hexagonal.cobranza.domain.model.Cobranza;
import um.tesoreria.aura.hexagonal.cobranza.domain.ports.out.AuraPaymentEventProducerPort;
import um.tesoreria.aura.hexagonal.cobranza.infrastructure.messaging.event.AuraPaymentProcessedEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuraPaymentKafkaProducer implements AuraPaymentEventProducerPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.aura.kafka.topic:aura-payments-topic}")
    private String auraPaymentsTopic;

    @Override
    public void publishPaymentEvent(Cobranza cobranza) {
        String eventKey = cobranza.getCpe() + "_" + (cobranza.getDebtId() != null ? cobranza.getDebtId() : "CBU");

        AuraPaymentProcessedEvent event = AuraPaymentProcessedEvent.builder()
                .cpe(cobranza.getCpe())
                .debtId(cobranza.getDebtId())
                .idConvenio(cobranza.getIdConvenio())
                .nombreConvenio(cobranza.getNombreCortoConvenio())
                .empresaCuit(cobranza.getEmpresaCuit())
                .importeCobrado(cobranza.getImporteCobrado())
                .fechaCobro(cobranza.getFechaCobro())
                .servicioNombre(cobranza.getServicioNombre())
                .nombreEntidadQueCobro(cobranza.getNombreEntidadQueCobro())
                .fechaCalculadaTransferencia(cobranza.getFechaCalculadaTransferencia())
                .eventTimestamp(cobranza.getEventTimestamp())
                .rawPaymentJson(cobranza.getRawPaymentJson())
                .build();

        log.info("Publishing payment event to Kafka topic [{}] for key [{}]...", auraPaymentsTopic, eventKey);
        kafkaTemplate.send(auraPaymentsTopic, eventKey, event);
    }

}
