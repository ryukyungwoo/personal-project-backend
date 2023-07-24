package kr.eddi.demo.scheduler;

import kr.eddi.demo.domain.stock.service.StockService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StockScheduler {
    private final StockService stockService;

    public StockScheduler(StockService stockService) {
        this.stockService = stockService;
    }

    @Scheduled(fixedDelay = 10800000)
    public void scheduleSaveOpinion() {
        stockService.saveOpinion();
    }

    @Scheduled(fixedDelay = 10800000)
    public void scheduleSaveOCVAData() {
        stockService.saveOCVAData();
    }
}
