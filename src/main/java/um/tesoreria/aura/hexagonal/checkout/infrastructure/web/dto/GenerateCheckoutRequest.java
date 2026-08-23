package um.tesoreria.aura.hexagonal.checkout.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateCheckoutRequest {

    private Long chequeraCuotaId;
    private UUID reservaVacanteId;

    @NotBlank(message = "El CPE es obligatorio")
    @Size(min = 19, max = 19, message = "El CPE debe tener exactamente 19 dígitos")
    @Pattern(regexp = "^\\d{19}$", message = "El CPE debe ser una cadena numérica de 19 dígitos")
    private String cpe;

    @NotBlank(message = "El Identificador de Deuda es obligatorio")
    @Size(min = 5, max = 5, message = "El Identificador de Deuda debe tener exactamente 5 dígitos")
    @Pattern(regexp = "^\\d{5}$", message = "El Identificador de Deuda debe ser una cadena numérica de 5 dígitos (Versión + MMAA)")
    private String debtId;

}
