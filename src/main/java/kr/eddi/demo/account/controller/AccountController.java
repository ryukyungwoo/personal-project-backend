package kr.eddi.demo.account.controller;

import kr.eddi.demo.account.controller.form.AccountLoginRequestForm;
import kr.eddi.demo.account.controller.form.AccountRegisterRequestFrom;
import kr.eddi.demo.account.service.AccountService;
import kr.eddi.demo.account.service.request.AccountLoginRequest;
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
    public String accountLogin (@RequestBody AccountLoginRequestForm requestForm) {
        String userToken = accountService.login(requestForm.toAccountRequest());
        return userToken;
    }
}
