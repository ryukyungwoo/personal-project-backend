package kr.eddi.demo.domain.account.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.eddi.demo.config.EncoderConfig;
import kr.eddi.demo.domain.account.controller.form.AccountLoginRequestForm;
import kr.eddi.demo.domain.account.controller.form.AccountRegisterRequestFrom;
import kr.eddi.demo.domain.account.entity.Account;
import kr.eddi.demo.domain.account.repository.AccountRepository;
import kr.eddi.demo.domain.account.service.request.AccountLoginRequest;
import kr.eddi.demo.domain.account.service.request.AccountRegisterRequest;
import kr.eddi.demo.util.jwt.JwtUtils;
import kr.eddi.demo.util.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    final private EncoderConfig encoderConfig;
    @Override
    public boolean register(AccountRegisterRequestFrom requestFrom) {
        AccountRegisterRequest request = requestFrom.toAccountRegisterRequest();
        String encodedPassword = encoderConfig.passwordEncoder().encode(request.getPassword());
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

        if(maybeAccount.isEmpty()){
            log.info("존재하지 않는 이메일 입니다.");
            return false;
        }

        Account account = maybeAccount.get();

        if(!encoderConfig.passwordEncoder().matches(request.getPassword(), maybeAccount.get().getPassword())){
            log.info("비밀번호가 잘못되었습니다.");
            return false;
        }

        final int ACCESS_TOKEN_EXPIRY_DATE = 6 * 60 * 60 * 1000;
        final int REFRESH_TOKEN_EXPIRY_DATE = 2 * 7 * 24 * 60 * 60 * 1000;

        final String refreshTokenUUID = UUID.randomUUID().toString();

        String accessToken = jwtUtils.generateToken(account.getEmail(), ACCESS_TOKEN_EXPIRY_DATE);
        String refreshToken = jwtUtils.generateToken(refreshTokenUUID , REFRESH_TOKEN_EXPIRY_DATE);

        redisService.setKeyAndValue(refreshToken, account.getId());

        final int ACCESS_COOKIE_EXPIRY_DATE = 60 * 60 * 6;
        final int REFRESH_COOKIE_EXPIRY_DATE = 60 * 60 * 24 * 14;


        Cookie assessCookie = jwtUtils.generateCookie("AccessToken", accessToken,
                ACCESS_COOKIE_EXPIRY_DATE, false);
        Cookie refreshCookie = jwtUtils.generateCookie("RefreshToken", refreshToken,
                REFRESH_COOKIE_EXPIRY_DATE, true);

        response.addCookie(assessCookie);
        response.addCookie(refreshCookie);

        return true;
        }

    @Override
    public void signOut(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("AccessToken")) {

                    String accessToken = cookie.getValue();
                    System.out.println("클라이언트에서 가져온 accessToken: " + accessToken);

                    Cookie assessCookie = jwtUtils.generateCookie("AccessToken", null, 0, false);

                    response.addCookie(assessCookie);
                }
                if(cookie.getName().equals("RefreshToken")) {
                    String refreshToken = cookie.getValue();
                    System.out.println("클라이언트에서 가져온 refreshToken: " + refreshToken);

                    Cookie refreshCookie = jwtUtils.generateCookie("RefreshToken", null, 0, true);
                    response.addCookie(refreshCookie);

                    redisService.deleteByKey(refreshToken);
                }
            }
        }

    }

    @Override
    public Account findLoginUserByEmail(String email) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);
        if (maybeAccount.isEmpty()){
            return null;
        }
        return maybeAccount.get();

    }

    @Override
    public Optional<Account> findUserByAccountId(Long accountId) {
        return accountRepository.findById(accountId);
    }
}
