package kr.eddi.demo.domain.board.service;

import kr.eddi.demo.domain.board.controller.form.request.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.controller.form.request.CommentDeleteRequestForm;
import kr.eddi.demo.domain.board.controller.form.request.CommentRegisterRequestForm;
import kr.eddi.demo.domain.board.controller.form.response.BoardRegisterResponseForm;
import kr.eddi.demo.domain.board.controller.form.response.BoardRequestResponseForm;
import kr.eddi.demo.domain.board.controller.form.response.CommentResponseForm;

import java.util.List;

public interface BoardService {

    List<BoardRequestResponseForm> list(String ticker);

    BoardRegisterResponseForm register(BoardRegisterRequestForm requestForm, String ticker);

    BoardRequestResponseForm request(String ticker, Long id);

    BoardRequestResponseForm modify(BoardRegisterRequestForm requestForm, String ticker, Long id);
    void delete(String ticker, Long id);

    void commentRegister(CommentRegisterRequestForm requestForm);

    List<CommentResponseForm> commentsListResponse(Long id);

    Boolean commentDelete(CommentDeleteRequestForm requestForm);
}
