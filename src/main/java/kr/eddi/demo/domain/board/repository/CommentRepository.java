package kr.eddi.demo.domain.board.repository;

import kr.eddi.demo.domain.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}