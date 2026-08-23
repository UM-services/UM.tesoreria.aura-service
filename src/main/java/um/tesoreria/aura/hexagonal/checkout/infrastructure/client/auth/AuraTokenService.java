package um.tesoreria.aura.hexagonal.checkout.infrastructure.client.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import um.tesoreria.aura.configuration.AuraProperties;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.client.auth.dto.AuraTokenResponse;
import um.tesoreria.aura.hexagonal.checkout.infrastructure.client.auth.feign.AuraAuthFeignClient;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuraTokenService {

    private final AuraAuthFeignClient authFeignClient;
    private final AuraProperties auraProperties;

    private String cachedToken = null;
    private Instant tokenExpiration = Instant.MIN;

    public synchronized String getValidToken() {
        // Renovar con margen de seguridad de 60 segundos antes de expirar
        if (cachedToken != null && Instant.now().plusSeconds(60).isBefore(tokenExpiration)) {
            return cachedToken;
        }

        log.debug("Requesting new OAuth2 token from Aura GIRE...");
        Map<String, String> form = new HashMap<>();
        form.put("grant_type", "client_credentials");
        form.put("scope", "openid");
        form.put("client_id", auraProperties.getClientId());
        form.put("client_secret", auraProperties.getClientSecret());

        try {
            AuraTokenResponse response = authFeignClient.getAccessToken(form);
            if (response != null && response.getAccessToken() != null) {
                this.cachedToken = response.getAccessToken();
                long expiresIn = response.getExpiresIn() != null ? response.getExpiresIn() : 3600L;
                this.tokenExpiration = Instant.now().plusSeconds(expiresIn);
                log.info("Aura OAuth2 token obtained successfully. Expires in {} seconds.", expiresIn);
                return this.cachedToken;
            }
        } catch (Exception e) {
            log.error("Failed to authenticate against Aura OAuth2 service: {}", e.getMessage());
            throw new RuntimeException("Error obtaining Aura OAuth2 token: " + e.getMessage(), e);
        }

        throw new RuntimeException("Empty response received from Aura OAuth2 service");
    }

}
