package um.tesoreria.aura.hexagonal.cobranza.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.aura.hexagonal.cobranza.domain.model.AuraConvenio;
import um.tesoreria.aura.hexagonal.cobranza.domain.model.Cobranza;
import um.tesoreria.aura.hexagonal.cobranza.domain.ports.in.PollCobranzasUseCase;
import um.tesoreria.aura.hexagonal.cobranza.domain.ports.out.AuraCobranzasPort;
import um.tesoreria.aura.hexagonal.cobranza.domain.ports.out.AuraCoreConvenioPort;
import um.tesoreria.aura.hexagonal.cobranza.domain.ports.out.AuraPaymentEventProducerPort;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PollCobranzasUseCaseImpl implements PollCobranzasUseCase {

    private final AuraCoreConvenioPort auraCoreConvenioPort;
    private final AuraCobranzasPort auraCobranzasPort;
    private final AuraPaymentEventProducerPort auraPaymentEventProducerPort;

    @Override
    public int pollCobranzas(LocalDate fechaDesde, LocalDate fechaHasta) {
        log.info("Starting Aura cobranzas polling between {} and {}...", fechaDesde, fechaHasta);
        List<AuraConvenio> activeConvenios = auraCoreConvenioPort.findActiveConvenios();

        if (activeConvenios == null || activeConvenios.isEmpty()) {
            log.warn("No active Aura convenios found in tesoreria-core.");
            return 0;
        }

        int totalProcessed = 0;

        for (AuraConvenio convenio : activeConvenios) {
            if (convenio.getNumeroConvenio() == null) {
                continue;
            }

            try {
                log.debug("Polling cobranzas for convenio {} ({})...", convenio.getNumeroConvenio(), convenio.getNombre());
                List<Cobranza> cobranzas = auraCobranzasPort.fetchCobranzas(convenio.getNumeroConvenio(), fechaDesde, fechaHasta);

                if (cobranzas != null && !cobranzas.isEmpty()) {
                    log.info("Found {} payment records for convenio {}.", cobranzas.size(), convenio.getNumeroConvenio());
                    for (Cobranza cobranza : cobranzas) {
                        auraPaymentEventProducerPort.publishPaymentEvent(cobranza);
                        totalProcessed++;
                    }
                } else {
                    log.debug("No new payments found for convenio {}.", convenio.getNumeroConvenio());
                }
            } catch (Exception e) {
                log.error("Error polling cobranzas for convenio {}: {}", convenio.getNumeroConvenio(), e.getMessage());
            }
        }

        log.info("Aura cobranzas polling completed. Total payments published to Kafka: {}", totalProcessed);
        return totalProcessed;
    }

}
