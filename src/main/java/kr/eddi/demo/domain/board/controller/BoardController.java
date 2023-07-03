package kr.eddi.demo.domain.board.controller;

import kr.eddi.demo.domain.board.controller.form.BoardModifyRequestForm;
import kr.eddi.demo.domain.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.entity.Board;
import kr.eddi.demo.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    final private BoardService boardService;

//    @GetMapping("/list")
//    public List<Board> boardList () {
//        log.info("boardList()");
//
//        List<Board> returnedBoardList = boardService.list();
//        log.info("returnedBoardList: " + returnedBoardList);
//
//        return returnedBoardList;
//    }
    @GetMapping("/list/{ticker}")
    public List<Board> requestBoards (@PathVariable("ticker") String ticker) {

        List<Board> boardList = boardService.list(ticker);
        log.info("requestBoards: " + boardList);

        return boardList;
    }

    @PostMapping("/register/{ticker}")
    public Board registerBoard (@RequestBody BoardRegisterRequestForm requestForm,
                                @PathVariable("ticker") String ticker) {
        log.info("registerBoard()");

        return boardService.register(requestForm, ticker);
    }
}
