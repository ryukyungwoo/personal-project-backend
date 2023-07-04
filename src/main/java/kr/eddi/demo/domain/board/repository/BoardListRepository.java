package kr.eddi.demo.domain.board.repository;

import kr.eddi.demo.domain.board.entity.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardListRepository extends JpaRepository<BoardList, Long> {
}
