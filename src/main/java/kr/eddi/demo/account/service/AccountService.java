package kr.eddi.demo.account.service;

import kr.eddi.demo.account.controller.form.AccountLoginRequestForm;
import kr.eddi.demo.account.service.request.AccountLogOutRequest;
import kr.eddi.demo.account.service.request.AccountLoginRequest;
import kr.eddi.demo.account.service.request.AccountRegisterRequest;

public interface AccountService {
    boolean register(AccountRegisterRequest toAccountRegisterRequest);

    String signIn(AccountLoginRequest request);

    void signOut(AccountLogOutRequest request);
}
