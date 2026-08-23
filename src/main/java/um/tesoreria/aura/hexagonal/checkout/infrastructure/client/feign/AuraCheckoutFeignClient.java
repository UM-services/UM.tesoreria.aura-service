package um.tesoreria.aura.hexagonal.checkout.infrastructure.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.client.dto.AuraCheckoutApiRequest;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.client.dto.AuraCheckoutApiResponse;

@FeignClient(name = "auraCheckoutClient", url = "${app.aura.base-url}")
public interface AuraCheckoutFeignClient {

    @PostMapping("/aura-api/v0/checkout-links/cpe/{cpe}")
    AuraCheckoutApiResponse generateCheckoutLink(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("cpe") String cpe,
            @RequestBody AuraCheckoutApiRequest request
    );

}
