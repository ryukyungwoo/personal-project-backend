package kr.eddi.demo.domain.chat.entity;

import jakarta.persistence.*;
import kr.eddi.demo.domain.account.entity.Account;
import kr.eddi.demo.domain.stock.entity.Stock;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_ticker")
    private Stock stock;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
