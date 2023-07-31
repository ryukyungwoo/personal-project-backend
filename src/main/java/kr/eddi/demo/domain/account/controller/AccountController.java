package kr.eddi.demo.domain.account.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.eddi.demo.domain.account.controller.form.*;
import kr.eddi.demo.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/request-nickname/{value}")
    public AccountNicknameResponseForm  accountNicknameResponseForm (@PathVariable("value") String accessToken) {
        return accountService.responseAccountNickname(accessToken);
    }
    @PostMapping("/get-my-page/{value}")
    public MyPageResponseForm myPageResponseForm (@PathVariable("value") String value){
        return accountService.getMyPage(value);
    }
}
