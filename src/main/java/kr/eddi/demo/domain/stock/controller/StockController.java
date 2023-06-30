package kr.eddi.demo.domain.stock.controller;

import kr.eddi.demo.domain.stock.controller.form.StockDataSaveRequestForm;
import kr.eddi.demo.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {
    final private StockService stockService;

    @GetMapping("/save-data")
    public void saveStockData ( ) {

        String requestSaveUrl = "http://localhost:8000/stock/save-data";

        stockService.save(requestSaveUrl);
    }
}
