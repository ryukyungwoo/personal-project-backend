package kr.eddi.demo.account.service.request;

import kr.eddi.demo.account.entity.Account;
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
