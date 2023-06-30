package kr.eddi.demo.domain.account.service.request;

import kr.eddi.demo.domain.account.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRegisterRequest {
    @Getter
    final private String email;
    final private String password;

    public Account toAccount () {
        return new Account(email, password);
    }
}
