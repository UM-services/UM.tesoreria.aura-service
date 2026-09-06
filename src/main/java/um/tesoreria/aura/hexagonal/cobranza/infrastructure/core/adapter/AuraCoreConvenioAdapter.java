package um.tesoreria.aura.hexagonal.cobranza.infrastructure.core.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.aura.hexagonal.cobranza.domain.model.AuraConvenio;
import um.tesoreria.aura.hexagonal.cobranza.domain.ports.out.AuraCoreConvenioPort;
import um.tesoreria.aura.hexagonal.cobranza.infrastructure.core.dto.AuraConvenioCoreResponse;
import um.tesoreria.aura.hexagonal.cobranza.infrastructure.core.feign.AuraCoreClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuraCoreConvenioAdapter implements AuraCoreConvenioPort {

    private final AuraCoreClient auraCoreClient;

    @Override
    public List<AuraConvenio> findActiveConvenios() {
        try {
            List<AuraConvenioCoreResponse> response = auraCoreClient.findActiveConvenios();
            if (response == null || response.isEmpty()) {
                return Collections.emptyList();
            }

            List<AuraConvenio> convenios = new ArrayList<>();
            for (AuraConvenioCoreResponse item : response) {
                convenios.add(AuraConvenio.builder()
                        .auraConvenioId(item.getAuraConvenioId())
                        .numeroConvenio(item.getNumeroConvenio())
                        .nombre(item.getNombre())
                        .cuit(item.getCuit())
                        .facultadId(item.getFacultadId())
                        .activo(item.getActivo())
                        .build());
            }
            return convenios;
        } catch (Exception e) {
            log.error("Error retrieving active convenios from tesoreria-core: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

}
