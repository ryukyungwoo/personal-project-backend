package kr.eddi.demo.domain.stock.controller.form.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockOpinionResponseForm {
    private String ticker;
    private String stockName;
    private int totalSentimentScore;
    private int positiveCount;
    private int negativeCount;
    private int naturalCount;
}
