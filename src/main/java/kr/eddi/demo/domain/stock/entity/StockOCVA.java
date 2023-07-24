package kr.eddi.demo.domain.stock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockOCVA {
    @Id
    @Column(unique=true)
    private String ticker;
    private String stockName;
    private Long open;
    private Long close;
    private Float rangeValue;
    private Float fluctuationRate;
    private Long volume;
    private Long amount;

    public StockOCVA(String ticker, Long open, Long close, Float rangeValue, Float fluctuationRate, Long volume, Long amount) {
        this.ticker = ticker;
        this.open = open;
        this.close = close;
        this.rangeValue = rangeValue;
        this.fluctuationRate = fluctuationRate;
        this.volume = volume;
        this.amount = amount;
    }
}
