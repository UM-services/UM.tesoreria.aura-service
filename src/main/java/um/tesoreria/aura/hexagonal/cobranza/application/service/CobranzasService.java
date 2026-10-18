package um.tesoreria.aura.hexagonal.cobranza.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import um.tesoreria.aura.hexagonal.cobranza.domain.ports.in.PollCobranzasUseCase;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class CobranzasService {

    private final PollCobranzasUseCase pollCobranzasUseCase;

    public int executePolling(LocalDate fechaDesde, LocalDate fechaHasta) {
        return pollCobranzasUseCase.pollCobranzas(fechaDesde, fechaHasta);
    }

}
