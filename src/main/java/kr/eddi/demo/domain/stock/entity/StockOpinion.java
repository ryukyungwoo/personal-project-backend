package kr.eddi.demo.domain.stock.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class StockOpinion {
    @Id
    private String id;
    private int totalSentimentScore;
    private int positiveCount;
    private int negativeCount;
    private int naturalCount;
    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_ticker")
    private Stock stock;
    @Builder
    public StockOpinion(int totalSentimentScore, int positiveCount, int negativeCount, int naturalCount) {
        this.totalSentimentScore = totalSentimentScore;
        this.positiveCount = positiveCount;
        this.negativeCount = negativeCount;
        this.naturalCount = naturalCount;
    }
}
