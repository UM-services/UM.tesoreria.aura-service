package um.tesoreria.aura.hexagonal.cobranza.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuraConvenio {

    private Integer auraConvenioId;
    private Integer numeroConvenio;
    private String nombre;
    private String cuit;
    private Integer facultadId;
    private Byte activo;

}
