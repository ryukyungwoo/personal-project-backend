package kr.eddi.demo.domain.account.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.eddi.demo.domain.account.controller.form.*;
import kr.eddi.demo.domain.account.entity.Account;
import kr.eddi.demo.domain.account.service.request.AccountLogOutRequest;
import kr.eddi.demo.domain.account.service.request.AccountLoginRequest;
import kr.eddi.demo.domain.account.service.request.AccountRegisterRequest;

import java.util.Date;
import java.util.Optional;

public interface AccountService {
    boolean register(AccountRegisterRequestFrom requestFrom);

    Date signIn(AccountLoginRequestForm requestForm, HttpServletResponse response);

    void signOut(HttpServletRequest request, HttpServletResponse response);

    Account findLoginUserByEmail(String email);

    Optional<Account> findUserByAccountId(Long accountId);

    boolean checkEmailDuplicate(CheckEmailDuplicateRequestForm requestForm);

    boolean checkNicknameDuplicate(CheckNicknameDuplicateRequestForm requestForm);

    boolean checkPhoneNumberDuplicate(CheckPhoneNumberDuplicateRequestForm requestForm);

    AccountNicknameResponseForm responseAccountNickname(String accessToken);

    MyPageResponseForm getMyPage(String value);
}
