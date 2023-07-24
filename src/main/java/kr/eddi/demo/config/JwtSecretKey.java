package kr.eddi.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
@Getter
public class JwtSecretKey {
        @Value("${jwt.secretKey}")
        private String secretKey;

}
