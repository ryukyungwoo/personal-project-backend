package kr.eddi.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
@Getter
public class JwtSecretKeyConfig {
        @Value("${spring.jwt.secretKey}")
        private String secretKey;

}
