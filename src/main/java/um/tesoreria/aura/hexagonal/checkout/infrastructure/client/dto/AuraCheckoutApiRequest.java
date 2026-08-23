package um.tesoreria.aura.hexagonal.checkout.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuraCheckoutApiRequest {

    @JsonProperty("debt_id")
    private String debtId;

}
