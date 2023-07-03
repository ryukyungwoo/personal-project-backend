package kr.eddi.demo.domain.board.service;

import kr.eddi.demo.domain.board.controller.form.BoardModifyRequestForm;
import kr.eddi.demo.domain.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.entity.Board;

import java.util.List;

public interface BoardService {

    List<Board> list(String ticker);

    Board register(BoardRegisterRequestForm requestForm, String ticker);

    Board read(String ticker, Long id);

    void modify(BoardRegisterRequestForm requestForm, String ticker, Long id);

    void delete(String ticker, Long id);
}
