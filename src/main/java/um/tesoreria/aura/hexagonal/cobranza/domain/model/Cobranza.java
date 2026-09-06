package um.tesoreria.aura.hexagonal.cobranza.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cobranza {

    private String empresaCuit;
    private String empresaRazonSocial;
    private Long idConvenio;
    private String nombreCortoConvenio;
    private String cpe;
    private String debtId;
    private LocalDate fechaPublicacion;
    private LocalDate fechaPrimerVencimiento;
    private BigDecimal montoPrimerVencimiento;
    private BigDecimal importeCobrado;
    private LocalDate fechaCobro;
    private String servicioNombre;
    private String nombreEntidadQueCobro;
    private LocalDate fechaCalculadaTransferencia;
    private OffsetDateTime eventTimestamp;
    private String rawPaymentJson;

}
