package um.tesoreria.aura.hexagonal.cobranza.infrastructure.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import um.tesoreria.aura.hexagonal.cobranza.infrastructure.client.dto.AuraCobranzaItemResponse;

import java.util.List;

@FeignClient(name = "auraCobranzasClient", url = "${app.aura.base-url}")
public interface AuraCobranzasFeignClient {

    @GetMapping("/aura-api/v0/cobranzas")
    List<AuraCobranzaItemResponse> getCobranzas(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("nro_empresa") String nroEmpresa,
            @RequestParam(value = "fecha_desde", required = false) String fechaDesde,
            @RequestParam(value = "fecha_hasta", required = false) String fechaHasta
    );

}
