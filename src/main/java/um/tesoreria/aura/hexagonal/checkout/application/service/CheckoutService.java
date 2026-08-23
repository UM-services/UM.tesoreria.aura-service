package um.tesoreria.aura.hexagonal.checkout.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import um.tesoreria.aura.hexagonal.checkout.domain.model.CheckoutLink;
import um.tesoreria.aura.hexagonal.checkout.domain.ports.in.GenerateCheckoutLinkUseCase;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckoutService {

    private final GenerateCheckoutLinkUseCase generateCheckoutLinkUseCase;

    public CheckoutLink generateCheckout(CheckoutLink checkoutLink) {
        return generateCheckoutLinkUseCase.generate(checkoutLink);
    }

    public List<CheckoutLink> generateCheckoutBulk(List<CheckoutLink> checkoutLinks) {
        return generateCheckoutLinkUseCase.generateBulk(checkoutLinks);
    }

}
