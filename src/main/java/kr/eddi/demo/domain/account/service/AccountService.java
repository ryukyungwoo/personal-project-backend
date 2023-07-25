package kr.eddi.demo.domain.account.service;

import jakarta.servlet.http.HttpServletResponse;
import kr.eddi.demo.domain.account.controller.form.AccountLoginRequestForm;
import kr.eddi.demo.domain.account.controller.form.AccountRegisterRequestFrom;
import kr.eddi.demo.domain.account.service.request.AccountLogOutRequest;
import kr.eddi.demo.domain.account.service.request.AccountLoginRequest;
import kr.eddi.demo.domain.account.service.request.AccountRegisterRequest;

public interface AccountService {
    boolean register(AccountRegisterRequestFrom requestFrom);

    Boolean signIn(AccountLoginRequestForm requestForm, HttpServletResponse response);

    void signOut(AccountLogOutRequest request);
}
