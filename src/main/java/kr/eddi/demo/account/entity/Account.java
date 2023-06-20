package kr.eddi.demo.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String email;
    @Getter
    private String password;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
