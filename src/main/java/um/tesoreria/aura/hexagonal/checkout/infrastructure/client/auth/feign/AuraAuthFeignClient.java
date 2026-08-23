package um.tesoreria.aura.hexagonal.checkout.infrastructure.client.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.client.auth.dto.AuraTokenResponse;

import java.util.Map;

@FeignClient(name = "auraAuthClient", url = "${app.aura.base-url}")
public interface AuraAuthFeignClient {

    @PostMapping(value = "/oauth-aura/oauth2/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AuraTokenResponse getAccessToken(@RequestBody Map<String, ?> formParams);

}
