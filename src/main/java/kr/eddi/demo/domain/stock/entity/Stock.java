package kr.eddi.demo.domain.stock.entity;

import jakarta.persistence.*;
import kr.eddi.demo.domain.board.entity.Board;
import kr.eddi.demo.domain.board.entity.StockBoardList;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Stock {
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
}
