package kr.eddi.demo.domain.board.repository;

import kr.eddi.demo.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<List<Board>> findByStockTicker(String ticker);
}
