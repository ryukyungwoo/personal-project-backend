package kr.eddi.demo.util.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.eddi.demo.domain.account.entity.Account;
import kr.eddi.demo.domain.account.service.AccountService;
import kr.eddi.demo.util.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    final private AccountService accountService;
    final private RedisService redisService;
    final private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final int ACCESS_TOKEN_EXPIRY_DATE = 6 * 60 * 60 * 1000;

        Cookie[] cookies =  request.getCookies();

        if(cookies == null){
            log.info("쿠키가 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = null;
        String refreshToken = null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                accessToken = cookie.getValue();
                log.info("검증할 accessToken: " + accessToken);
                break;
            }
        }

        if (accessToken == null) {
            log.info("accessToken이 만료되었습니다.");
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("RefreshToken")) {
                    refreshToken = cookie.getValue();
                    log.info("검증할 refreshToken: " + refreshToken);
                    break;
                }
            }
            if (refreshToken == null) {
                log.info("refreshToken이 없습니다.");
                return;
            }
            if (redisService.getValueByKey(refreshToken) == null) {
                log.info("로그아웃된 토큰입니다.");
                return;
            }
            if (jwtUtils.isExpired(refreshToken)) {
                log.info("refreshToken이 만료되었습니다.");
                return;
            }

            Long accountId = redisService.getValueByKey(refreshToken);
            Optional<Account> maybeAccount = accountService.findUserByAccountId(accountId);

            if (maybeAccount.isEmpty()) {
                return;
            }

            Account account = maybeAccount.get();
            String email = account.getEmail();
            accessToken = jwtUtils.generateToken(email, ACCESS_TOKEN_EXPIRY_DATE);

            final int ACCESS_COOKIE_EXPIRY_DATE = 60 * 60 * 6;

            Cookie newAccessTokenCookie = jwtUtils.generateCookie("AccessToken", accessToken, ACCESS_COOKIE_EXPIRY_DATE, false);

            response.addCookie(newAccessTokenCookie);

            log.info("accessToken 토큰 재발행: " + accessToken);
        }

        String email = jwtUtils.getEmail(accessToken);

        Account account = accountService.findLoginUserByEmail(email);
        log.info("JwtFilter.account: " + account);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        account.getEmail(), null, null);
        log.info("authenticationToken: " + authenticationToken);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
