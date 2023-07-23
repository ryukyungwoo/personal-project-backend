package kr.eddi.demo.domain.stock.controller.form.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockOCVAResponseForm {
    private String stockName;
    private String open;
    private String close;
    private Float rangeValue;
    private Float fluctuationRate;
    private String volume;
    private String amount;
}
