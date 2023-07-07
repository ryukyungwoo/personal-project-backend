package kr.eddi.demo.domain.board.controller;

import kr.eddi.demo.domain.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.controller.form.BoardRequestResponseForm;
import kr.eddi.demo.domain.board.controller.form.BoardResponseForm;
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

    @GetMapping("/list/{ticker}")
    public List<Board> requestBoards (@PathVariable("ticker") String ticker) {
        log.info("ticker" + ticker);

        List<Board> boards = boardService.list(ticker);

        return boards;
    }

    @PostMapping("/register/{ticker}")
    public BoardResponseForm registerBoard (@RequestBody BoardRegisterRequestForm requestForm,
                                            @PathVariable("ticker") String ticker) {

        return boardService.register(requestForm, ticker);
    }
    @GetMapping("/{ticker}/{orderNumber}")
    public BoardRequestResponseForm requestBoard (@PathVariable("ticker") String ticker,
                               @PathVariable("orderNumber") Integer orderNumber) {

        return boardService.request(ticker, orderNumber);
    }
    @PutMapping("/{ticker}/{id}")
    public void modifyBoard (@RequestBody BoardRegisterRequestForm requestForm,
                              @PathVariable("ticker") String ticker,
                              @PathVariable("id") Long id) {

        boardService.modify(requestForm, ticker, id);
    }
    @DeleteMapping("/{ticker}/{id}")
    public void deleteBoard (@PathVariable("ticker") String ticker,
                             @PathVariable("id") Long id) {
        boardService.delete(ticker, id);
    }
}
