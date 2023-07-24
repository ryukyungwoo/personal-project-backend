package kr.eddi.demo.util.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.eddi.demo.config.JwtSecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {
    final private JwtSecretKey jwtSecretKey;
    public String generateToken(String email, String secretKey, long validTimeMs) {

        String token = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + validTimeMs))
                .compact();
        return token;
    }
}
