package kr.eddi.demo.domain.board.service;

import kr.eddi.demo.domain.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.entity.Board;

import java.util.List;

public interface BoardService {
    List<Board> list();

    Board register(BoardRegisterRequestForm requestForm);
}
