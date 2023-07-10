package kr.eddi.demo.accountMockingTest;

import kr.eddi.demo.domain.account.controller.form.AccountRegisterRequestFrom;
import kr.eddi.demo.domain.account.entity.Account;
import kr.eddi.demo.domain.account.repository.AccountRepository;
import kr.eddi.demo.domain.account.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;


@SpringBootTest
public class AccountMockingTest {
    @Mock
    private AccountRepository mockRepository;
    @InjectMocks
    private AccountServiceImpl mockService;

    @BeforeEach
    public void setup () throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("AccountMocking Test: 회원등록")
    public void 회원을_등록합니다 () {
        final AccountRegisterRequestFrom requestFrom = new AccountRegisterRequestFrom("이메일", "작성자");

        final Account account = requestFrom.toAccountRegisterRequest().toAccount();
        when(mockRepository.save(account)).thenReturn(new Account("이메일", "작성자"));
    }

}
