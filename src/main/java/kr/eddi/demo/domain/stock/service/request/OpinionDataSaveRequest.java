package kr.eddi.demo.domain.stock.service.request;

import kr.eddi.demo.domain.stock.entity.StockOpinion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpinionDataSaveRequest {
    private int totalSentimentScore;
    private int positiveCount;
    private int negativeCount;
    private int naturalCount;

    public StockOpinion toStockOpinionMining() {
        return new StockOpinion(totalSentimentScore, positiveCount, negativeCount, naturalCount);
    }
}
