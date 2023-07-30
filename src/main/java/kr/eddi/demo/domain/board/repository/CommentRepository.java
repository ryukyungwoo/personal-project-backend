package kr.eddi.demo.domain.board.repository;

import kr.eddi.demo.domain.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.board b LEFT JOIN FETCH c.account a LEFT JOIN FETCH a.accountNickname WHERE b.id = :boardId")
    List<Comment> findByBoardId(@Param("boardId") Long id);

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.account a LEFT JOIN FETCH a.accountNickname WHERE c.id = :selectedCommentId")
    Optional<Comment> findByIdWithLazy(@Param("selectedCommentId") Long selectedCommentId);
}
