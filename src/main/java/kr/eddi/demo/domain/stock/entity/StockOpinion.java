package kr.eddi.demo.domain.stock.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class StockOpinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalSentimentScore;
    private int positiveCount;
    private int negativeCount;
    private int naturalCount;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_ticker")
    private Stock stock;

    public StockOpinion(int totalSentimentScore, int positiveCount, int negativeCount, int naturalCount) {
        this.totalSentimentScore = totalSentimentScore;
        this.positiveCount = positiveCount;
        this.negativeCount = negativeCount;
        this.naturalCount = naturalCount;
    }
}
