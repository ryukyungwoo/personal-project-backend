package kr.eddi.demo.account.service;

import kr.eddi.demo.account.controller.form.AccountLoginRequestForm;
import kr.eddi.demo.account.entity.Account;
import kr.eddi.demo.account.repository.AccountRepository;
import kr.eddi.demo.account.service.request.AccountLogOutRequest;
import kr.eddi.demo.account.service.request.AccountLoginRequest;
import kr.eddi.demo.account.service.request.AccountRegisterRequest;
import kr.eddi.demo.redis.RedisService;
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
    final private RedisService redisService;
    @Override
    public boolean register(AccountRegisterRequest request) {

        final Account account = accountRepository.save(request.toAccount());
        return true;
    }

    @Override
    public String signIn(AccountLoginRequest request) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(request.getEmail());
        log.info(String.valueOf(maybeAccount));
        if(maybeAccount.isEmpty()){
            log.info("존재하지 않는 이메일 입니다.abc");
        }
        Account account = maybeAccount.get();
        if(!account.getPassword().equals(request.getPassword())){
            log.info("비밀번호가 잘못되었습니다.def");
        }

        redisService.setKeyAndValue(request.getUserToken(), account.getId());
        return request.getUserToken();
        }

    @Override
    public void signOut(AccountLogOutRequest request) {

        redisService.deleteByKey(request.getUserToken());

    }
}
