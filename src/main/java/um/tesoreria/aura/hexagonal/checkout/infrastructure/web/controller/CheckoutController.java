package um.tesoreria.aura.hexagonal.checkout.infrastructure.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import um.tesoreria.aura.hexagonal.checkout.application.exception.CheckoutException;
import um.tesoreria.aura.hexagonal.checkout.application.service.CheckoutService;
import um.tesoreria.aura.hexagonal.checkout.domain.model.CheckoutLink;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.web.dto.GenerateCheckoutRequest;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.web.dto.GenerateCheckoutResponse;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.web.mapper.CheckoutDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tesoreria/aura/checkout")
@Slf4j
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final CheckoutDtoMapper checkoutDtoMapper;

    @PostMapping("/generate")
    public ResponseEntity<GenerateCheckoutResponse> generateCheckoutLink(@Valid @RequestBody GenerateCheckoutRequest request) {
        log.debug("REST request to generate checkout link for CPE: {}", request.getCpe());
        CheckoutLink domain = checkoutDtoMapper.toDomain(request);

        try {
            CheckoutLink generated = checkoutService.generateCheckout(domain);
            return ResponseEntity.ok(checkoutDtoMapper.toResponse(generated));
        } catch (CheckoutException e) {
            log.error("CheckoutException: [{}] {}", e.getErrorCode(), e.getMessage());
            HttpStatus status = mapStatus(e.getHttpStatus(), e.getErrorCode());
            throw new ResponseStatusException(status, e.getMessage(), e);
        }
    }

    @PostMapping("/generate-bulk")
    public ResponseEntity<List<GenerateCheckoutResponse>> generateCheckoutLinksBulk(@Valid @RequestBody List<GenerateCheckoutRequest> requests) {
        log.debug("REST request to generate bulk checkout links for {} items", requests.size());
        List<CheckoutLink> domains = requests.stream()
                .map(checkoutDtoMapper::toDomain)
                .collect(Collectors.toList());

        List<CheckoutLink> results = checkoutService.generateCheckoutBulk(domains);
        List<GenerateCheckoutResponse> responses = results.stream()
                .map(checkoutDtoMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    private HttpStatus mapStatus(int httpStatus, String errorCode) {
        if ("DEBT_NOT_FOUND".equalsIgnoreCase(errorCode) || "CPE_NOT_FOUND".equalsIgnoreCase(errorCode)) {
            return HttpStatus.NOT_FOUND;
        }
        if ("DEBT_ALREADY_PAID".equalsIgnoreCase(errorCode) || "DEBT_ALL_DUE_EXPIRED".equalsIgnoreCase(errorCode)) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }
        if ("PASS_NOT_ALLOWED_CONVENIO".equalsIgnoreCase(errorCode)) {
            return HttpStatus.FORBIDDEN;
        }
        if ("TOO_MANY_REQUESTS".equalsIgnoreCase(errorCode) || httpStatus == 429) {
            return HttpStatus.TOO_MANY_REQUESTS;
        }
        if (httpStatus >= 400 && httpStatus < 500) {
            return HttpStatus.valueOf(httpStatus);
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
