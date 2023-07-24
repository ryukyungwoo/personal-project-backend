package kr.eddi.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedOrigins(Arrays.asList(
                "http://127.0.0.1:8080",
                "http://localhost:8080",
                "http://13.125.154.146:8080",
                "http://127.0.0.1:8000",
                "http://localhost:8000",
                "http://13.125.154.146:8000")
        );
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowCredentials(true);

        http.csrf().disable()
                .authorizeRequests(authz -> authz
                        .anyRequest().permitAll() // 모든 요청은 인증되지 않은 사용자도 허용합니다.
                )
                .httpBasic().disable()
                .cors(cors -> cors.configurationSource(request -> corsConfiguration));

        return http.build();
    }
}
