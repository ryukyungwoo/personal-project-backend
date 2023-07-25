package kr.eddi.demo.domain.account.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.eddi.demo.domain.account.controller.form.AccountLoginRequestForm;
import kr.eddi.demo.domain.account.controller.form.AccountRegisterRequestFrom;
import kr.eddi.demo.domain.account.entity.Account;
import kr.eddi.demo.domain.account.repository.AccountRepository;
import kr.eddi.demo.domain.account.service.request.AccountLogOutRequest;
import kr.eddi.demo.domain.account.service.request.AccountLoginRequest;
import kr.eddi.demo.domain.account.service.request.AccountRegisterRequest;
import kr.eddi.demo.util.jwt.JwtUtils;
import kr.eddi.demo.util.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    final private JwtUtils jwtUtils;
    final private AccountRepository accountRepository;
    final private RedisService redisService;
    final private BCryptPasswordEncoder passwordEncoder;
    @Override
    public boolean register(AccountRegisterRequestFrom requestFrom) {
        AccountRegisterRequest request = requestFrom.toAccountRegisterRequest();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Account account = new Account().builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .build();
        accountRepository.save(account);
        return true;
    }

    @Override
    public Boolean signIn(AccountLoginRequestForm requestForm, HttpServletResponse response) {

        AccountLoginRequest request = requestForm.toAccountRequest();
        Optional<Account> maybeAccount = accountRepository.findByEmail(request.getEmail());

        log.info(String.valueOf(maybeAccount));

        if(maybeAccount.isEmpty()){
            log.info("존재하지 않는 이메일 입니다.");
            return false;
        }

        Account account = maybeAccount.get();

        if(!passwordEncoder.matches(request.getPassword(), maybeAccount.get().getPassword())){
            log.info("비밀번호가 잘못되었습니다.");
            return false;
        }

        final int ACCESS_TOKEN_EXPIRY_DATE = 6 * 60 * 60 * 1000;
        final int REFRESH_TOKEN_EXPIRY_DATE = 2 * 7 * 24 * 60 * 60 * 1000;

        final String refreshTokenUUID = UUID.randomUUID().toString();

        String accessToken = jwtUtils.generateToken(account.getEmail(), ACCESS_TOKEN_EXPIRY_DATE);
        String refreshToken = jwtUtils.generateToken(refreshTokenUUID , REFRESH_TOKEN_EXPIRY_DATE);

        System.out.println(accessToken);
        System.out.println(refreshToken);

        redisService.setKeyAndValue(refreshToken, account.getId());

        Cookie assessCookie = jwtUtils.generateCookie("AccessToken", accessToken,
                ACCESS_TOKEN_EXPIRY_DATE, false);
        Cookie refreshCookie = jwtUtils.generateCookie("RefreshToken", refreshToken,
                REFRESH_TOKEN_EXPIRY_DATE, true);

        System.out.println(assessCookie);
        System.out.println(refreshCookie);

        response.addCookie(assessCookie);
        response.addCookie(refreshCookie);

        return true;
        }

    @Override
    public void signOut(AccountLogOutRequest request) {

        redisService.deleteByKey(request.getUserToken());

    }
}
