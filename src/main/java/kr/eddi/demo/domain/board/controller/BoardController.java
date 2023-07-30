package kr.eddi.demo.domain.board.controller;

import kr.eddi.demo.domain.board.controller.form.request.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.controller.form.request.CommentDeleteRequestForm;
import kr.eddi.demo.domain.board.controller.form.request.CommentRegisterRequestForm;
import kr.eddi.demo.domain.board.controller.form.response.BoardRequestResponseForm;
import kr.eddi.demo.domain.board.controller.form.response.BoardRegisterResponseForm;
import kr.eddi.demo.domain.board.controller.form.response.CommentResponseForm;
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
    public List<BoardRequestResponseForm> requestBoards (@PathVariable("ticker") String ticker) {

        return boardService.list(ticker);
    }

    @PostMapping("/register/{ticker}")
    public BoardRegisterResponseForm registerBoard (@RequestBody BoardRegisterRequestForm requestForm,
                                                    @PathVariable("ticker") String ticker) {

        return boardService.register(requestForm, ticker);
    }
    @GetMapping("/{ticker}/{id}")
    public BoardRequestResponseForm requestBoard (@PathVariable("ticker") String ticker,
                               @PathVariable("id") Long id) {

        return boardService.request(ticker, id);
    }
    @PutMapping("/{ticker}/{id}")
    public BoardRequestResponseForm modifyBoard (@RequestBody BoardRegisterRequestForm requestForm,
                              @PathVariable("ticker") String ticker,
                              @PathVariable("id") Long id) {

        return boardService.modify(requestForm, ticker, id);
    }
    @DeleteMapping("/{ticker}/{id}")
    public void deleteBoard (@PathVariable("ticker") String ticker,
                             @PathVariable("id") Long id) {
        boardService.delete(ticker, id);
    }
    @PostMapping("/comment/register")
    public void registerComment (@RequestBody CommentRegisterRequestForm requestForm) {
        boardService.commentRegister(requestForm);
    }
    @GetMapping("/comment/{id}")
    public List<CommentResponseForm> responseComments (@PathVariable("id") Long id) {
        return boardService.commentsListResponse(id);
    }
    @PostMapping("/comment/delete")
    public Boolean deleteComment (@RequestBody CommentDeleteRequestForm requestForm) {
        return boardService.commentDelete(requestForm);
    }
}
