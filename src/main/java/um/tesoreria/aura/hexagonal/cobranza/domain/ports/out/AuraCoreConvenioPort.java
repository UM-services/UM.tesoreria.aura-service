package um.tesoreria.aura.hexagonal.cobranza.domain.ports.out;

import um.tesoreria.aura.hexagonal.cobranza.domain.model.AuraConvenio;

import java.util.List;

public interface AuraCoreConvenioPort {

    List<AuraConvenio> findActiveConvenios();

}
