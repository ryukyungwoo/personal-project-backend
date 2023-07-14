package kr.eddi.demo.domain.stock.controller.form.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StockNameResponseForm {
    private String ticker;
    private String stockName;
}
