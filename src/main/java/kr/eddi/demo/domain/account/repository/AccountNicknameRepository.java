package kr.eddi.demo.domain.account.repository;

import kr.eddi.demo.domain.account.entity.AccountNickname;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountNicknameRepository extends JpaRepository<AccountNickname, Long> {
    Optional<AccountNickname> findByNickname(String nickName);
}
