package um.tesoreria.aura.hexagonal.cobranza.infrastructure.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuraConvenioCoreResponse {

    private Integer auraConvenioId;
    private Integer numeroConvenio;
    private String nombre;
    private String cuit;
    private Integer facultadId;
    private Byte activo;

}
