package kr.eddi.demo.domain.board.service.request;

import kr.eddi.demo.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardModifyRequest {
    private String title;
    private String content;
    private String writer;
    public Board toBoard () {
        return new Board(title, content, writer);
    }
}
