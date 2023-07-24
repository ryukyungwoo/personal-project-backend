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
    private String ticker;

    private String stockName;
    private Long open;
    private Long close;
    private Float rangeValue;
    private Float fluctuationRate;
    private Long volume;
    private Long amount;
}
