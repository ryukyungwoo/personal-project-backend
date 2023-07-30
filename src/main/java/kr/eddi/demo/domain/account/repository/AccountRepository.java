package kr.eddi.demo.domain.account.repository;

import kr.eddi.demo.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    @Query("SELECT a FROM Account a JOIN FETCH a.accountDetail JOIN FETCH a.accountNickname WHERE a.email = :email")
    Optional<Account> findByEmailWithLazy(String email);

    @Query("SELECT a FROM Account a JOIN FETCH a.accountDetail JOIN FETCH a.accountNickname WHERE a.accountNickname.nickname = :nickname")
    Optional<Account> findByNicknameWithLazy(String nickname);
}
