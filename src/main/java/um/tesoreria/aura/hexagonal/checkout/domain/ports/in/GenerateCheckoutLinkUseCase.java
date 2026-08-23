package um.tesoreria.aura.hexagonal.checkout.domain.ports.in;

import um.tesoreria.aura.hexagonal.checkout.domain.model.CheckoutLink;

import java.util.List;

public interface GenerateCheckoutLinkUseCase {

    CheckoutLink generate(CheckoutLink checkoutLink);

    List<CheckoutLink> generateBulk(List<CheckoutLink> checkoutLinks);

}
