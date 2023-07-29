package kr.eddi.demo.domain.account.repository;

import kr.eddi.demo.domain.account.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {
}
