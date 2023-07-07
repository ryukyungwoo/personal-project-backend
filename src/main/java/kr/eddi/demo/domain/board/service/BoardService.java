package kr.eddi.demo.domain.board.service;

import kr.eddi.demo.domain.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.controller.form.BoardRequestResponseForm;
import kr.eddi.demo.domain.board.controller.form.BoardResponseForm;
import kr.eddi.demo.domain.board.entity.Board;

import java.util.List;

public interface BoardService {

    List<Board> list(String ticker);

    BoardResponseForm register(BoardRegisterRequestForm requestForm, String ticker);

    BoardRequestResponseForm request(String ticker, Integer orderNumber);

    void modify(BoardRegisterRequestForm requestForm, String ticker, Long id);

    void delete(String ticker, Long id);
}
