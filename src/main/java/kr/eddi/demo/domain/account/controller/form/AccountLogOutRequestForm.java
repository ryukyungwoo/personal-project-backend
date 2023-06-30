package kr.eddi.demo.domain.account.controller.form;

import kr.eddi.demo.domain.account.service.request.AccountLogOutRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountLogOutRequestForm {

    private String userToken;

    public AccountLogOutRequest toAccountLogOutRequest () {
        return new AccountLogOutRequest(userToken);
    }
}
