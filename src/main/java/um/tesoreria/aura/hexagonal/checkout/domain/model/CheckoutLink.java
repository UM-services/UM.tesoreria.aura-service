package um.tesoreria.aura.hexagonal.checkout.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutLink {

    private Long chequeraCuotaId;
    private UUID reservaVacanteId;
    private String cpe;
    private String debtId;
    private String checkoutLink;
    private String orderUid;
    private BigDecimal amount;
    private LocalDate dueDate;
    private String reference;
    private OffsetDateTime generatedAt;
    private String status;

}
