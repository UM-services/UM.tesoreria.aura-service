package um.tesoreria.aura.hexagonal.cobranza.infrastructure.client.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.client.auth.AuraTokenService;
import um.tesoreria.aura.hexagonal.cobranza.domain.model.Cobranza;
import um.tesoreria.aura.hexagonal.cobranza.domain.ports.out.AuraCobranzasPort;
import um.tesoreria.aura.hexagonal.cobranza.infrastructure.client.dto.AuraCobranzaItemResponse;
import um.tesoreria.aura.hexagonal.cobranza.infrastructure.client.feign.AuraCobranzasFeignClient;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuraCobranzasAdapter implements AuraCobranzasPort {

    private final AuraCobranzasFeignClient cobranzasFeignClient;
    private final AuraTokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    public List<Cobranza> fetchCobranzas(Integer numeroConvenio, LocalDate fechaDesde, LocalDate fechaHasta) {
        String token = tokenService.getValidToken();
        String authorization = "Bearer " + token;

        String fDesde = fechaDesde != null ? fechaDesde.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
        String fHasta = fechaHasta != null ? fechaHasta.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;

        try {
            List<AuraCobranzaItemResponse> items = cobranzasFeignClient.getCobranzas(
                    authorization,
                    String.valueOf(numeroConvenio),
                    fDesde,
                    fHasta
            );

            if (items == null || items.isEmpty()) {
                return Collections.emptyList();
            }

            List<Cobranza> result = new ArrayList<>();
            for (AuraCobranzaItemResponse item : items) {
                String rawJson = null;
                try {
                    rawJson = objectMapper.writeValueAsString(item);
                } catch (Exception ignored) {
                }

                result.add(Cobranza.builder()
                        .empresaCuit(item.getEmpresaCuit())
                        .empresaRazonSocial(item.getEmpresaRazonSocial())
                        .idConvenio(item.getIdConvenio())
                        .nombreCortoConvenio(item.getNombreCortoConvenio())
                        .cpe(item.getCodigoPagoElectronico())
                        .debtId(item.getNumeroFactura())
                        .fechaPublicacion(item.getFechaPublicacion())
                        .fechaPrimerVencimiento(item.getFechaPrimerVencimiento())
                        .montoPrimerVencimiento(item.getMontoPrimerVencimiento())
                        .importeCobrado(item.getImporteCobrado())
                        .fechaCobro(item.getFechaCobro())
                        .servicioNombre(item.getServicioNombre())
                        .nombreEntidadQueCobro(item.getNombreEntidadQueCobro())
                        .fechaCalculadaTransferencia(item.getFechaCalculadaTransferencia())
                        .eventTimestamp(OffsetDateTime.now())
                        .rawPaymentJson(rawJson)
                        .build());
            }

            return result;
        } catch (Exception e) {
            log.error("Error fetching cobranzas from Aura for convenio {}: {}", numeroConvenio, e.getMessage());
            throw new RuntimeException("Error en consulta de cobranzas de Aura: " + e.getMessage(), e);
        }
    }

}
