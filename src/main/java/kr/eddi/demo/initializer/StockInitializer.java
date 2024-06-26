package kr.eddi.demo.initializer;

import kr.eddi.demo.domain.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class StockInitializer {

//    @Bean
//    public CommandLineRunner onStartUp(StockService stockService) {
//        return args -> {
//            ExecutorService executorService = Executors.newFixedThreadPool(3);
//
//            executorService.submit(stockService::save);
//            executorService.submit(stockService::saveOpinion);
//            executorService.submit(stockService::saveOCVAData);
//
//            executorService.shutdown();
//        };
//    }
    @Bean
    public CommandLineRunner onStartUp(StockService stockService) {
        return args -> {
            log.info("Stock Initializer Start");
            stockService.save();
            log.info("Opinion Initializer Start");
            stockService.saveOpinion();
            log.info("OCVA Save Initializer Start");
            stockService.saveOCVAData();
        };
    }
}
