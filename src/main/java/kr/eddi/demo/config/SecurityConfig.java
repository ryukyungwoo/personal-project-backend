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
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

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

//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.applyPermitDefaultValues();
//        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        corsConfiguration.setAllowedOrigins(allowedOriginsConfig.getAllowedOrigins());
//
//        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
//        corsConfiguration.setAllowCredentials(true);
//
//        http.csrf().disable()
//                .authorizeRequests(authz -> authz
//                        .requestMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated()
//                        .anyRequest().permitAll()
//                )
//                .httpBasic().disable()
//                .cors(cors -> cors.configurationSource(request -> corsConfiguration))
//        .addFilterBefore(new JwtFilter(accountService, redisService, jwtUtils), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
        final String[] permitUrl = { "/", "/stock/**", "/chat/**"};

            return http
                    .httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilterBefore(new JwtFilter(accountService, redisService, jwtUtils), UsernamePasswordAuthenticationFilter.class)
                    .cors().and()
                    .authorizeRequests()
                    .requestMatchers(permitUrl).permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
                    .anyRequest().authenticated()
                    .and().build();

    }
}
