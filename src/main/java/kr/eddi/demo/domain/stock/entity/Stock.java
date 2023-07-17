package kr.eddi.demo.domain.stock.entity;

import jakarta.persistence.*;
//import kr.eddi.demo.domain.board.entity.StockBoardList;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @Column(unique=true)
    private String ticker;
    private String stockName;
}

