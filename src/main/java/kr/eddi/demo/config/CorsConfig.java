package kr.eddi.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {
    final private AllowedOriginsConfig allowedOriginsConfig;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:8000",
                        "http://localhost:8080",
                        "http://127.0.0.1:8000",
                        "http://127.0.0.1:8080",
                        "http://43.201.41.0:8000",
                        "http://43.201.41.0:8080"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.AUTHORIZATION)
                .allowCredentials(true);
    }
}