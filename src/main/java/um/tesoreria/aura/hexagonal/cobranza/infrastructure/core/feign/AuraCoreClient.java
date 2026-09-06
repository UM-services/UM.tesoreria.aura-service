package um.tesoreria.aura.hexagonal.cobranza.infrastructure.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import um.tesoreria.aura.hexagonal.cobranza.infrastructure.core.dto.AuraConvenioCoreResponse;

import java.util.List;

@FeignClient(name = "tesoreria-core-service", contextId = "auraCoreConvenioClient", path = "/api/tesoreria/core/aura")
public interface AuraCoreClient {

    @GetMapping("/convenio/active")
    List<AuraConvenioCoreResponse> findActiveConvenios();

}
