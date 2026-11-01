package um.tesoreria.aura.hexagonal.cobranza.domain.ports.out;

import um.tesoreria.aura.hexagonal.cobranza.domain.model.Cobranza;

import java.time.LocalDate;
import java.util.List;

public interface AuraCobranzasPort {

    List<Cobranza> fetchCobranzas(Integer numeroConvenio, LocalDate fechaDesde, LocalDate fechaHasta);

}
