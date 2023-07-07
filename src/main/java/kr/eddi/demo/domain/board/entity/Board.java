package kr.eddi.demo.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import kr.eddi.demo.domain.stock.entity.Stock;
import lombok.*;

import java.util.List;

@Entity
@ToString
@Getter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String title;
    private String writer;
    @Setter
    private String content;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "stock_board_list_id")
    private StockBoardList stockBoardList;
    public Board(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }
    public Board findBoardById(List<Board> boardList, Long id) {
        for (Board board : boardList) {
            if (board.getId().equals(id)) {
                return board;
            }
        }
        return null;
    }
}