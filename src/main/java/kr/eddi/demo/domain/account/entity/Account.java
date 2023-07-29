package kr.eddi.demo.domain.account.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    private AccountDetail accountDetail;
    @OneToOne(fetch = FetchType.LAZY)
    private AccountNickname accountNickname;

    @Builder
    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
