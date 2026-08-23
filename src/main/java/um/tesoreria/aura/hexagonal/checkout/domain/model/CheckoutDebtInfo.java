package um.tesoreria.aura.hexagonal.checkout.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutDebtInfo {

    private String cuit;
    private String convenioId;
    private String cpe;
    private String debtId;
    private BigDecimal amount;
    private LocalDate dueDate;

}
