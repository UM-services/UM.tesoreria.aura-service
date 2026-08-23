package um.tesoreria.aura.hexagonal.checkout.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuraErrorResponse {

    private String code;
    private String message;

}
