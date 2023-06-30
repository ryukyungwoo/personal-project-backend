package kr.eddi.demo.domain.board.controller;

import kr.eddi.demo.domain.board.controller.form.BoardModifyRequestForm;
import kr.eddi.demo.domain.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.entity.Board;
import kr.eddi.demo.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    final private BoardService boardService;

    @GetMapping("/list")
    public List<Board> boardList () {
        log.info("boardList()");

        List<Board> returnedBoardList = boardService.list();
        log.info("returnedBoardList: " + returnedBoardList);

        return returnedBoardList;
    }

    @PostMapping("/register")
    public Board registerBoard (@RequestBody BoardRegisterRequestForm requestForm) {
        log.info("registerBoard()");

        return boardService.register(requestForm);
    }

    @GetMapping("/{id}")
    public Board readBoard (@PathVariable("id") Long id) {
        log.info("boardRead()");

        return boardService.read(id);
    }

    @PutMapping("/{id}")
    public Board modifyBoard (@PathVariable("id") Long id,
                              @RequestBody BoardModifyRequestForm requestForm) {
        log.info("boardModify()");

        return boardService.modify(id, requestForm);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard (@PathVariable("id") Long id) {
        log.info("boardDelete()");

        boardService.delete(id);
    }
}
