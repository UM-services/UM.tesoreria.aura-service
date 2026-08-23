package um.tesoreria.aura.hexagonal.checkout.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuraCheckoutApiResponse {

    @JsonProperty("checkout_link")
    private String checkoutLink;

    @JsonProperty("order_uid")
    private String orderUid;

    @JsonProperty("debt")
    private DebtDetail debt;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("generated_at")
    private OffsetDateTime generatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DebtDetail {
        private String cuit;

        @JsonProperty("convenio_id")
        private String convenioId;

        private String cpe;

        @JsonProperty("debt_id")
        private String debtId;

        private BigDecimal amount;

        @JsonProperty("due_date")
        private LocalDate dueDate;
    }

}
