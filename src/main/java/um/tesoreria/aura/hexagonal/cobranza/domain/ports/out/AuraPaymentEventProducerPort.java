package um.tesoreria.aura.hexagonal.cobranza.domain.ports.out;

import um.tesoreria.aura.hexagonal.cobranza.domain.model.Cobranza;

public interface AuraPaymentEventProducerPort {

    void publishPaymentEvent(Cobranza cobranza);

}
