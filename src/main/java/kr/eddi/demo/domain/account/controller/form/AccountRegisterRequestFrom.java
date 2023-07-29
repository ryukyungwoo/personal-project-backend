package kr.eddi.demo.domain.account.controller.form;

import kr.eddi.demo.domain.account.service.request.AccountRegisterRequest;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegisterRequestFrom {

    private String email;

    private String password;
    private String nickname;
    private String phoneNumber;
    private String address;

    public AccountRegisterRequest toAccountRegisterRequest () {

        return new AccountRegisterRequest(email, password, nickname, phoneNumber, address);
    }
}
