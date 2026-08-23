package um.tesoreria.aura.hexagonal.checkout.domain.ports.out;

import um.tesoreria.aura.hexagonal.checkout.domain.model.CheckoutLink;

public interface AuraCheckoutPort {

    CheckoutLink requestCheckoutLink(String cpe, String debtId);

}
