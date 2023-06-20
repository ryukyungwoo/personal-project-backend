package kr.eddi.demo.account.controller.form;

import kr.eddi.demo.account.service.request.AccountRegisterRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRegisterRequestFrom {

    final private String email;

    final private String password;

    public AccountRegisterRequest toAccountRegisterRequest () {
        return new AccountRegisterRequest(email, password);
    }
}
