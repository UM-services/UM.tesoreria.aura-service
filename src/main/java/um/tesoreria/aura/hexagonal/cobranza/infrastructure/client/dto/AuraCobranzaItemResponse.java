package um.tesoreria.aura.hexagonal.cobranza.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuraCobranzaItemResponse {

    @JsonProperty("empresa_cuit")
    private String empresaCuit;

    @JsonProperty("empresa_razonSocial")
    private String empresaRazonSocial;

    @JsonProperty("id_convenio")
    private Long idConvenio;

    @JsonProperty("nombreCorto_Convenio")
    private String nombreCortoConvenio;

    @JsonProperty("codigo_pago_electronico")
    private String codigoPagoElectronico;

    @JsonProperty("numeroFactura")
    private String numeroFactura;

    @JsonProperty("fecha_publicacion")
    private LocalDate fechaPublicacion;

    @JsonProperty("fecha_1er_vencimiento")
    private LocalDate fechaPrimerVencimiento;

    @JsonProperty("monto_1er_vencimiento")
    private BigDecimal montoPrimerVencimiento;

    @JsonProperty("importe_cobrado")
    private BigDecimal importeCobrado;

    @JsonProperty("fecha_cobro")
    private LocalDate fechaCobro;

    @JsonProperty("servicio_nombre")
    private String servicioNombre;

    @JsonProperty("nombre_entidad_que_cobro")
    private String nombreEntidadQueCobro;

    @JsonProperty("fecha_calculada_transferencia")
    private LocalDate fechaCalculadaTransferencia;

}
