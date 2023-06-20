package kr.eddi.demo.account.service;

import kr.eddi.demo.account.controller.form.AccountLoginRequestForm;
import kr.eddi.demo.account.entity.Account;
import kr.eddi.demo.account.repository.AccountRepository;
import kr.eddi.demo.account.service.request.AccountRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    @Autowired
    final private AccountRepository accountRepository;

    @Override
    public boolean register(AccountRegisterRequest request) {
        final Optional<Account> maybeAccount = accountRepository.findByEmail(request.getEmail());
        if (maybeAccount.isPresent()) {
            return false;
        }

        final Account account = accountRepository.save(request.toAccount());
        return true;
    }

    @Override
    public boolean login(AccountLoginRequestForm requestForm) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(requestForm.getEmail());

        if(maybeAccount.isEmpty()){
            log.info("존재하지 않는 이메일 입니다.");
            return false;
        }
        Account account = maybeAccount.get();
        if(!account.getPassword().equals(requestForm.getPassword())){
            log.info("비밀번호가 잘못되었습니다.");
            return false;
        }
        return true;
        }
}
