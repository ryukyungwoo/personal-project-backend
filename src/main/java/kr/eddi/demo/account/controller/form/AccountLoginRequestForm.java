package kr.eddi.demo.account.controller.form;

import kr.eddi.demo.account.service.request.AccountLoginRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AccountLoginRequestForm {

    private String email;
    private String password;

    public AccountLoginRequest toAccountRequest () {
        UUID randomPrefix = UUID.randomUUID();
        String userToken = randomPrefix + email;

        return new AccountLoginRequest(email, password, userToken);
    }
}
