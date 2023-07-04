package kr.eddi.demo.domain.account.service;

import kr.eddi.demo.domain.account.service.request.AccountLogOutRequest;
import kr.eddi.demo.domain.account.service.request.AccountLoginRequest;
import kr.eddi.demo.domain.account.service.request.AccountRegisterRequest;

public interface AccountService {
    boolean register(AccountRegisterRequest toAccountRegisterRequest);

    String signIn(AccountLoginRequest request);

    void signOut(AccountLogOutRequest request);
}
