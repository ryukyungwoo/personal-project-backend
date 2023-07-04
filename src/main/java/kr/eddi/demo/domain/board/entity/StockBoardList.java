package kr.eddi.demo.domain.board.entity;

import jakarta.persistence.*;
import kr.eddi.demo.domain.stock.entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class StockBoardList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_ticker")
    private Stock stock;

    @Getter
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "boardList_id")
    private BoardList boardList;

    public StockBoardList(Stock stock, BoardList boardList) {
        this.stock = stock;
        this.boardList = boardList;
    }
}
