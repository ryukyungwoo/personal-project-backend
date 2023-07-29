package kr.eddi.demo.domain.chat.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.eddi.demo.domain.account.controller.form.AccountRegisterRequestFrom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class IpController {
    @GetMapping("/get-client-ip")
    public ResponseEntity<String> getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }
        return ResponseEntity.ok(clientIp);
    }
}
