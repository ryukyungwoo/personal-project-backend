package kr.eddi.demo.account.controller.form;

import kr.eddi.demo.account.service.request.AccountRegisterRequest;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegisterRequestFrom {

    private String email;

    private String password;

    public AccountRegisterRequest toAccountRegisterRequest () {
        return new AccountRegisterRequest(email, password);
    }
}
