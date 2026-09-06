package um.tesoreria.aura.hexagonal.cobranza.infrastructure.messaging.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuraPaymentProcessedEvent {

    private String cpe;
    private String debtId;
    private Long idConvenio;
    private String nombreConvenio;
    private String empresaCuit;
    private BigDecimal importeCobrado;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaCobro;

    private String servicioNombre;
    private String nombreEntidadQueCobro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaCalculadaTransferencia;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX")
    private OffsetDateTime eventTimestamp;

    private String rawPaymentJson;

}
