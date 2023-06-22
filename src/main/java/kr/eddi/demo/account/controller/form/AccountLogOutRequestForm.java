package kr.eddi.demo.account.controller.form;

import kr.eddi.demo.account.service.request.AccountLogOutRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountLogOutRequestForm {

    private String userToken;

    public AccountLogOutRequest toAccountLogOutRequest () {
        return new AccountLogOutRequest(userToken);
    }
}
