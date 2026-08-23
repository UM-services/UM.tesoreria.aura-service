package um.tesoreria.aura.hexagonal.checkout.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import um.tesoreria.aura.hexagonal.checkout.domain.model.CheckoutLink;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.web.dto.GenerateCheckoutRequest;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.web.dto.GenerateCheckoutResponse;

@Component
public class CheckoutDtoMapper {

    public CheckoutLink toDomain(GenerateCheckoutRequest request) {
        if (request == null) {
            return null;
        }
        return CheckoutLink.builder()
                .chequeraCuotaId(request.getChequeraCuotaId())
                .reservaVacanteId(request.getReservaVacanteId())
                .cpe(request.getCpe())
                .debtId(request.getDebtId())
                .build();
    }

    public GenerateCheckoutResponse toResponse(CheckoutLink domain) {
        if (domain == null) {
            return null;
        }
        return GenerateCheckoutResponse.builder()
                .chequeraCuotaId(domain.getChequeraCuotaId())
                .reservaVacanteId(domain.getReservaVacanteId())
                .cpe(domain.getCpe())
                .debtId(domain.getDebtId())
                .checkoutLink(domain.getCheckoutLink())
                .orderUid(domain.getOrderUid())
                .amount(domain.getAmount())
                .dueDate(domain.getDueDate())
                .reference(domain.getReference())
                .generatedAt(domain.getGeneratedAt())
                .status(domain.getStatus())
                .build();
    }

}
