package kr.eddi.demo.domain.account.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountLogOutRequest {
    final private String userToken;
}
