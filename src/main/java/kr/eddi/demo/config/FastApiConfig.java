package kr.eddi.demo.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FastApiConfig {
    @Value("${fast_api.app.url}")
    private String fastApiAppUrl;
}
