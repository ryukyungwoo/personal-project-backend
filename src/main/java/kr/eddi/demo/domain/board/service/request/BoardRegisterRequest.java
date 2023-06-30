package kr.eddi.demo.domain.board.service.request;

import kr.eddi.demo.domain.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class BoardRegisterRequest {
    private String title;
    private String content;
    private String writer;

    public BoardRegisterRequest(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Board toBoard () {
        return new Board(title, writer, content);
    }
}
