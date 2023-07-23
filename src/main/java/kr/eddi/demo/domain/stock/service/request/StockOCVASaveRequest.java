package kr.eddi.demo.domain.stock.service.request;

import kr.eddi.demo.domain.stock.entity.StockOCVA;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class StockOCVASaveRequest {
    private String ticker;
    private String open;
    private String close;
    private Float rangeValue;
    private Float fluctuationRate;
    private String volume;
    private String amount;

    public StockOCVA toStockOCVA () {

        return new StockOCVA(ticker, open, close, rangeValue, fluctuationRate, volume, amount);
    }
}
