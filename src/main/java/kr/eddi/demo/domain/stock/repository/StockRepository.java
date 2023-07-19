package kr.eddi.demo.domain.stock.repository;

import kr.eddi.demo.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {
    Optional<Stock> findByTicker(String ticker);
}

