package kr.eddi.demo.domain.stock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String ticker;
    @Getter
    @Setter
    private String stockName;

    public Stock(String ticker, String stockName) {
        this.ticker = ticker;
        this.stockName = stockName;
    }

    public Stock() {

    }
}
