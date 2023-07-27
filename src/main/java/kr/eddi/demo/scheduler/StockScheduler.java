package kr.eddi.demo.scheduler;

import kr.eddi.demo.domain.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class StockScheduler {
    private final StockService stockService;

    public StockScheduler(StockService stockService) {
        this.stockService = stockService;
    }

    @Scheduled(fixedDelay = 10800000)
    public void scheduleSaveOpinion() {
        log.info("Opinion Scheduler Start");

        stockService.saveOpinion();
    }

    @Scheduled(fixedDelay = 10800000)
    public void scheduleSaveOCVAData() {
        log.info("OCVA Save Scheduler Start");
        stockService.saveOCVAData();
    }
}
