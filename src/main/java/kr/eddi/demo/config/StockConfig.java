package kr.eddi.demo.config;

import jakarta.annotation.PostConstruct;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import kr.eddi.demo.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockConfig {

    final private StockRepository stockRepository;
    final private StockService stockService;

    @PostConstruct
    private void init() {
        log.debug("initializer start");

        initStockRepository();

        log.debug("initializer end");
    }

    private void initStockRepository() {
        try {
            String requestSaveUrl = "http://localhost:8000/stock/save-data";
            List<Stock> newStockList = stockService.getStockList(requestSaveUrl);

            List<Stock> existingStockList = stockRepository.findAll();

            Set<Stock> stockToSave = new HashSet<>();

            for (Stock newStock : newStockList) {
                boolean exists = false;
                for (Stock existingStock : existingStockList) {
                    if (Objects.equals(existingStock.getTicker(), newStock.getTicker())) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    stockToSave.add(newStock);
                }
            }

            if (!stockToSave.isEmpty()) {
                stockRepository.saveAll(stockToSave);
            }

        } catch (Exception e) {
            log.error("Error during stock initialization", e);
        }
    }
}
