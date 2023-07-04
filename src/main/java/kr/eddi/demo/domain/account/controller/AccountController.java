package kr.eddi.demo.domain.account.controller;

import kr.eddi.demo.domain.account.controller.form.AccountLogOutRequestForm;
import kr.eddi.demo.domain.account.controller.form.AccountLoginRequestForm;
import kr.eddi.demo.domain.account.controller.form.AccountRegisterRequestFrom;
import kr.eddi.demo.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    final private AccountService accountService;
    @PostMapping("/sign-up")
    public boolean accountRegister (@RequestBody AccountRegisterRequestFrom requestFrom) {
        return accountService.register(requestFrom.toAccountRegisterRequest());
    }

    @PostMapping("/sign-in")
    public String accountSignIn (@RequestBody AccountLoginRequestForm requestForm) {
        String userToken = accountService.signIn(requestForm.toAccountRequest());
        log.info("userToken" + userToken);
        return userToken;
    }
    @PostMapping("/sign-out")
    public void accountSignOut (@RequestBody AccountLogOutRequestForm requestForm) {
        log.info("로그아웃" + requestForm);
        accountService.signOut(requestForm.toAccountLogOutRequest());
    }
}
