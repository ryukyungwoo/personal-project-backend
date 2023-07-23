package kr.eddi.demo.domain.stock.controller;

import kr.eddi.demo.domain.stock.controller.form.response.StockNameResponseForm;
import kr.eddi.demo.domain.stock.controller.form.response.StockOCVAResponseForm;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@ToString
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
    @GetMapping("/name/{ticker}")
    public StockNameResponseForm responseStockName (@PathVariable("ticker") String ticker) {
       return stockService.getStockName(ticker);
    }
    @GetMapping("/opinion-mining")
    public void requestStockOpinion (){
        stockService.getOpinionTest();
    }
    @GetMapping("/save-OCVA-data")
    public void saveOHCLVAData () {
        stockService.getOCVAData();
    }
    @GetMapping("/list/{OCVA}/{ascending}")
    public List<StockOCVAResponseForm> stockListResponse (@PathVariable("OCVA") String OCVA,
                                                          @PathVariable("ascending") String ascending) {
        return stockService.list(OCVA, ascending);
    }
}
