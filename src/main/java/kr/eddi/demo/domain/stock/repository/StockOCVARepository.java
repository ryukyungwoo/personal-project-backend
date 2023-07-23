package kr.eddi.demo.domain.stock.repository;

import kr.eddi.demo.domain.stock.entity.StockOCVA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockOCVARepository extends JpaRepository<StockOCVA, String> {
}
