package kr.eddi.demo.domain.account.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.eddi.demo.domain.account.controller.form.*;
import kr.eddi.demo.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    final private AccountService accountService;
    @PostMapping("/sign-up")
    public boolean accountRegister (@RequestBody AccountRegisterRequestFrom requestFrom) {
        return accountService.register(requestFrom);
    }

    @PostMapping("/sign-in")
    public Date accountSignIn (@RequestBody AccountLoginRequestForm requestForm,
                               HttpServletResponse response) {
        return accountService.signIn(requestForm, response);
    }
    @PostMapping("/sign-out")
    public void accountSignOut (HttpServletRequest request, HttpServletResponse response) {
        accountService.signOut(request, response);
    }
    @PostMapping("/check-duplicate-email")
    public boolean checkEmail (@RequestBody CheckEmailDuplicateRequestForm requestForm) {
        return accountService.checkEmailDuplicate(requestForm);
    }
    @PostMapping("/check-duplicate-nickname")
    public boolean checkNickname (@RequestBody CheckNicknameDuplicateRequestForm requestForm) {
        return accountService.checkNicknameDuplicate(requestForm);
    }
    @PostMapping("/check-duplicate-phoneNumber")
    public boolean checkPhoneNumber (@RequestBody CheckPhoneNumberDuplicateRequestForm requestForm) {
        return accountService.checkPhoneNumberDuplicate(requestForm);
    }
}
