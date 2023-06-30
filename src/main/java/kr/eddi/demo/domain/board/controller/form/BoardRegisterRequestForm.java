package kr.eddi.demo.domain.board.controller.form;

import kr.eddi.demo.domain.board.service.request.BoardRegisterRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class BoardRegisterRequestForm {
    private String title;
    private String content;
    private String writer;

    public BoardRegisterRequest toBoardRegisterRequest() {
        return new BoardRegisterRequest(title, writer, content);
    }
}
