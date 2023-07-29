package kr.eddi.demo.domain.account.service.request;

import kr.eddi.demo.domain.account.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AccountRegisterRequest {
    final private String email;
    final private String password;
    final private String nickname;
    final private String phoneNumber;
    final private String address;

}
