package kr.eddi.demo.config;

import kr.eddi.demo.domain.account.service.AccountService;
import kr.eddi.demo.util.jwt.JwtFilter;
import kr.eddi.demo.util.jwt.JwtUtils;
import kr.eddi.demo.util.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    final private AllowedOriginsConfig allowedOriginsConfig;
    final private AccountService accountService;
    final private RedisService redisService;
    final private JwtUtils jwtUtils;
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {

            return http
                    .httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilterBefore(new JwtFilter(accountService, redisService, jwtUtils), UsernamePasswordAuthenticationFilter.class)
                    .cors().and()
                    .authorizeRequests()
                    .requestMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated()
                    .anyRequest().permitAll()
                    .and().build();

    }
}
