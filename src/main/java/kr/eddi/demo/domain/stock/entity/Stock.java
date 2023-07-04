package kr.eddi.demo.domain.stock.entity;

import jakarta.persistence.*;
import kr.eddi.demo.domain.board.entity.StockBoardList;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Stock {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @Getter
    @Setter
    @Column(unique=true)
    private String ticker;
    @Getter
    @Setter
    private String stockName;

    @OneToOne(mappedBy = "stock", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private StockBoardList stockBoardList;

    public Stock(String ticker, String stockName) {
        this.ticker = ticker;
        this.stockName = stockName;
    }

    public Stock() {

    }
}
