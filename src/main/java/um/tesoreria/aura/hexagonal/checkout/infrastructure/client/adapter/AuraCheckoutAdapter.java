package um.tesoreria.aura.hexagonal.checkout.infrastructure.client.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.aura.hexagonal.checkout.application.exception.CheckoutException;
import um.tesoreria.aura.hexagonal.checkout.domain.model.CheckoutLink;
import um.tesoreria.aura.hexagonal.checkout.domain.ports.out.AuraCheckoutPort;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.client.auth.AuraTokenService;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.client.dto.AuraCheckoutApiRequest;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.client.dto.AuraCheckoutApiResponse;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.client.dto.AuraErrorResponse;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.client.feign.AuraCheckoutFeignClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuraCheckoutAdapter implements AuraCheckoutPort {

    private final AuraCheckoutFeignClient checkoutFeignClient;
    private final AuraTokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    public CheckoutLink requestCheckoutLink(String cpe, String debtId) {
        String token = tokenService.getValidToken();
        String authorizationHeader = "Bearer " + token;

        AuraCheckoutApiRequest request = AuraCheckoutApiRequest.builder()
                .debtId(debtId)
                .build();

        try {
            AuraCheckoutApiResponse response = checkoutFeignClient.generateCheckoutLink(authorizationHeader, cpe, request);
            return mapToDomain(cpe, debtId, response);
        } catch (FeignException e) {
            log.error("Error from Aura API (Status {}): {}", e.status(), e.contentUTF8());
            String errorCode = "AURA_API_ERROR";
            String errorMessage = "Error en comunicación con GIRE / Aura";

            try {
                if (e.contentUTF8() != null && !e.contentUTF8().isBlank()) {
                    AuraErrorResponse errorObj = objectMapper.readValue(e.contentUTF8(), AuraErrorResponse.class);
                    if (errorObj.getCode() != null) {
                        errorCode = errorObj.getCode();
                    }
                    if (errorObj.getMessage() != null) {
                        errorMessage = errorObj.getMessage();
                    }
                }
            } catch (Exception parseException) {
                log.warn("Could not parse Aura error payload: {}", parseException.getMessage());
            }

            throw new CheckoutException(errorMessage, errorCode, e.status() > 0 ? e.status() : 500);
        }
    }

    private CheckoutLink mapToDomain(String cpe, String debtId, AuraCheckoutApiResponse response) {
        CheckoutLink.CheckoutLinkBuilder builder = CheckoutLink.builder()
                .cpe(cpe)
                .debtId(debtId)
                .checkoutLink(response.getCheckoutLink())
                .orderUid(response.getOrderUid())
                .reference(response.getReference())
                .generatedAt(response.getGeneratedAt())
                .status("GENERATED");

        if (response.getDebt() != null) {
            builder.amount(response.getDebt().getAmount())
                   .dueDate(response.getDebt().getDueDate());
        }

        return builder.build();
    }

}
