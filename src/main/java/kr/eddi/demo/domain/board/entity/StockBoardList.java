package kr.eddi.demo.domain.board.entity;

import jakarta.persistence.*;
import kr.eddi.demo.domain.stock.entity.Stock;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
public class StockBoardList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    final private Long id;
    @Getter
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_ticker")
    final private Stock stock;

    @Getter
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id")
    final private List<Board> boardList = new ArrayList<>();
}
