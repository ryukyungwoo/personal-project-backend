package kr.eddi.demo.domain.board.entity;

import jakarta.persistence.*;
import kr.eddi.demo.domain.stock.entity.Stock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
public class StockBoardList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_ticker")
    private Stock stock;
    @Getter
    @Setter
    @OneToMany(mappedBy = "stockBoardList", fetch = FetchType.LAZY)
    private List<Board> boards;
    public StockBoardList(Stock stock, List<Board> boards) {
        this.stock = stock;
        this.boards = boards;
    }
}
