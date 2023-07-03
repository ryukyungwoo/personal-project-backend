package kr.eddi.demo.domain.board.repository;

import kr.eddi.demo.domain.board.entity.StockBoardList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockBoardListRepository extends JpaRepository<StockBoardList, Long> {
    Optional<StockBoardList> findByStockTicker(String ticker);
}
