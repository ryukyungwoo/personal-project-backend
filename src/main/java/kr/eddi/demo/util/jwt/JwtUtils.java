package kr.eddi.demo.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
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
    public String generateToken(String email, long expiryDate) {

        String token = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey.getSecretKey())
                .setExpiration(new Date(System.currentTimeMillis() + expiryDate))
                .compact();
        return token;
    }
    public Claims parseJwtToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecretKey.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Error during parsing jwt token", e);
            return null;
        }
    }
    public Cookie generateCookie(String tokenName, String token, int expiryDate, Boolean httponly) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setPath("/");
        cookie.setHttpOnly(httponly);
        cookie.setMaxAge(expiryDate);

        return cookie;
    }
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
    public String getEmail(String token) {
        return extractClaims(token).getSubject();
    }
}
