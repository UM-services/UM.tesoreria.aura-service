package um.tesoreria.aura.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.aura")
public class AuraProperties {

    private String baseUrl = "https://services-dev.api.gire.com/aura/homologacion";
    private String clientId = "";
    private String clientSecret = "";

}
