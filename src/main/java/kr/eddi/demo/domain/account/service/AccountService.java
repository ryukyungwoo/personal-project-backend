package kr.eddi.demo.domain.account.service;

import kr.eddi.demo.domain.account.controller.form.AccountRegisterRequestFrom;
import kr.eddi.demo.domain.account.service.request.AccountLogOutRequest;
import kr.eddi.demo.domain.account.service.request.AccountLoginRequest;
import kr.eddi.demo.domain.account.service.request.AccountRegisterRequest;

public interface AccountService {
    boolean register(AccountRegisterRequestFrom requestFrom);

    String signIn(AccountLoginRequest request);

    void signOut(AccountLogOutRequest request);
}
