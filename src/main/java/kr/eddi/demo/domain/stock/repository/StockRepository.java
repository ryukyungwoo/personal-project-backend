package kr.eddi.demo.domain.stock.repository;

import kr.eddi.demo.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
