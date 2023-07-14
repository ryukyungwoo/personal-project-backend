package kr.eddi.demo.domain.board.controller.form.request;

import kr.eddi.demo.domain.board.service.request.BoardRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class BoardRegisterRequestForm {
    private String title;
    private String content;
    private String writer;

    public BoardRegisterRequestForm(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public BoardRegisterRequest toBoardRegisterRequest() {
        return new BoardRegisterRequest(title, writer, content);
    }
}