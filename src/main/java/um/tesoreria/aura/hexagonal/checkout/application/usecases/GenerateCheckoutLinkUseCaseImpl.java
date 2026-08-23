package um.tesoreria.aura.hexagonal.checkout.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.aura.hexagonal.checkout.domain.model.CheckoutLink;
import um.tesoreria.aura.hexagonal.checkout.domain.ports.in.GenerateCheckoutLinkUseCase;
import um.tesoreria.aura.hexagonal.checkout.domain.ports.out.AuraCheckoutPort;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenerateCheckoutLinkUseCaseImpl implements GenerateCheckoutLinkUseCase {

    private final AuraCheckoutPort auraCheckoutPort;

    @Override
    public CheckoutLink generate(CheckoutLink checkoutLink) {
        log.debug("Generating checkout link for CPE: {} and Debt ID: {}", checkoutLink.getCpe(), checkoutLink.getDebtId());
        CheckoutLink result = auraCheckoutPort.requestCheckoutLink(checkoutLink.getCpe(), checkoutLink.getDebtId());
        
        result.setChequeraCuotaId(checkoutLink.getChequeraCuotaId());
        result.setReservaVacanteId(checkoutLink.getReservaVacanteId());
        return result;
    }

    @Override
    public List<CheckoutLink> generateBulk(List<CheckoutLink> checkoutLinks) {
        log.debug("Generating bulk checkout links for {} items", checkoutLinks.size());
        List<CheckoutLink> results = new ArrayList<>();
        
        for (CheckoutLink item : checkoutLinks) {
            try {
                CheckoutLink generated = generate(item);
                results.add(generated);
            } catch (Exception e) {
                log.error("Error generating checkout link for cuota {}: {}", item.getChequeraCuotaId(), e.getMessage());
                item.setStatus("ERROR");
                results.add(item);
            }
        }
        return results;
    }

}
