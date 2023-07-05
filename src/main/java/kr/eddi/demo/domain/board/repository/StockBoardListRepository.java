package kr.eddi.demo.domain.board.repository;

import kr.eddi.demo.domain.board.entity.StockBoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StockBoardListRepository extends JpaRepository<StockBoardList, Long> {
    @Query("SELECT sbl FROM StockBoardList sbl JOIN FETCH sbl.boardList bl JOIN FETCH bl.boards WHERE sbl.stock.ticker = :ticker")
    Optional<StockBoardList> findByStockTicker(String ticker);
}
