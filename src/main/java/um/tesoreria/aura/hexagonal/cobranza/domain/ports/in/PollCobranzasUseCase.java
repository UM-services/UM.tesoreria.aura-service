package um.tesoreria.aura.hexagonal.cobranza.domain.ports.in;

import java.time.LocalDate;

public interface PollCobranzasUseCase {

    int pollCobranzas(LocalDate fechaDesde, LocalDate fechaHasta);

}
