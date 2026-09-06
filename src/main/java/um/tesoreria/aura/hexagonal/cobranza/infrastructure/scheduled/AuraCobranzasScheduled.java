package um.tesoreria.aura.hexagonal.cobranza.infrastructure.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import um.tesoreria.aura.hexagonal.cobranza.application.service.CobranzasService;

import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuraCobranzasScheduled {

    private final CobranzasService cobranzasService;

    // Ejecución cada 15 minutos para reconciliación online de pagos del día
    @Scheduled(cron = "${app.aura.scheduler.cron:0 */15 * * * *}")
    public void scheduleOnlineCobranzasPolling() {
        log.info("Scheduled task triggered: Polling Aura online cobranzas for today...");
        LocalDate today = LocalDate.now();
        try {
            int processed = cobranzasService.executePolling(today, today);
            log.info("Scheduled task finished. Processed {} online payments.", processed);
        } catch (Exception e) {
            log.error("Error during scheduled Aura online cobranzas polling: {}", e.getMessage());
        }
    }

    // Ejecución nocturna diaria (02:00 AM) para consolidar canales batch (PMC, Link Pagos, Débitos)
    @Scheduled(cron = "${app.aura.scheduler.cron-nightly:0 0 2 * * *}")
    public void scheduleNightlyCobranzasPolling() {
        log.info("Scheduled task triggered: Polling Aura nightly cobranzas for yesterday...");
        LocalDate yesterday = LocalDate.now().minusDays(1);
        try {
            int processed = cobranzasService.executePolling(yesterday, yesterday);
            log.info("Nightly scheduled task finished. Processed {} batch payments.", processed);
        } catch (Exception e) {
            log.error("Error during scheduled Aura nightly cobranzas polling: {}", e.getMessage());
        }
    }

}
