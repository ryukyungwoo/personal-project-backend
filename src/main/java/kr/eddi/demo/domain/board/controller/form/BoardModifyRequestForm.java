package kr.eddi.demo.domain.board.controller.form;

import kr.eddi.demo.domain.board.service.request.BoardModifyRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardModifyRequestForm {
    private String title;
    private String content;
    private String writer;
    public BoardModifyRequest toBoardModifyRequest() {
        return new BoardModifyRequest(title, writer, content);
    }
}
