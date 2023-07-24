package kr.eddi.demo.domain.stock.controller.form.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.eddi.demo.domain.stock.service.request.StockOCVASaveRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StockOCVASaveRequestForm {

    @JsonProperty("티커")
    private String ticker;

    @JsonProperty("시가")
    private Long open;

    @JsonProperty("종가")
    private Long close;

    @JsonProperty("변동폭")
    private Float rangeValue;

    @JsonProperty("등락률")
    private Float fluctuationRate;

    @JsonProperty("거래량")
    private Long volume;

    @JsonProperty("거래대금")
    private Long amount;

    public StockOCVASaveRequest toStockOCVASaveRequest(){
        return new StockOCVASaveRequest(ticker, open, close, rangeValue, fluctuationRate, volume, amount);
    }
}
