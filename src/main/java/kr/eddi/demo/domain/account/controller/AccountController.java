package kr.eddi.demo.domain.account.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        return accountService.register(requestFrom);
    }

    @PostMapping("/sign-in")
    public Boolean accountSignIn (@RequestBody AccountLoginRequestForm requestForm,
                                  HttpServletResponse response) {
        return accountService.signIn(requestForm, response);
    }
    @PostMapping("/sign-out")
    public void accountSignOut (HttpServletRequest request, HttpServletResponse response) {
        accountService.signOut(request, response);
    }
}
