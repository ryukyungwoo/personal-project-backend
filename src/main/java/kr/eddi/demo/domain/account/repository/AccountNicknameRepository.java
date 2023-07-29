package kr.eddi.demo.domain.account.repository;

import kr.eddi.demo.domain.account.entity.AccountNickname;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountNicknameRepository extends JpaRepository<AccountNickname, Long> {
}
