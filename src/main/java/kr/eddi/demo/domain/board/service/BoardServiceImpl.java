package kr.eddi.demo.domain.board.service;

import kr.eddi.demo.domain.account.entity.Account;
import kr.eddi.demo.domain.account.repository.AccountRepository;
import kr.eddi.demo.domain.board.controller.form.request.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.controller.form.request.CommentDeleteRequestForm;
import kr.eddi.demo.domain.board.controller.form.request.CommentRegisterRequestForm;
import kr.eddi.demo.domain.board.controller.form.response.BoardRegisterResponseForm;
import kr.eddi.demo.domain.board.controller.form.response.BoardRequestResponseForm;
import kr.eddi.demo.domain.board.controller.form.response.CommentResponseForm;
import kr.eddi.demo.domain.board.entity.Board;
import kr.eddi.demo.domain.board.entity.Comment;
import kr.eddi.demo.domain.board.repository.BoardRepository;
import kr.eddi.demo.domain.board.repository.CommentRepository;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@ToString
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    final private BoardRepository boardRepository;
    final private StockRepository stockRepository;
    final private AccountRepository accountRepository;
    final private CommentRepository commentRepository;

    @Override
    public List<BoardRequestResponseForm> list(String ticker) {
        Optional<List<Board>> maybeBoardList = boardRepository.findByStockTicker(ticker);

        if (maybeBoardList.isEmpty()) {
            return null;
        }

        List<Board> boardList = maybeBoardList.get();
        List<BoardRequestResponseForm> requestResponseFormList = new ArrayList<>();

        for (Board board : boardList) {

            if (board.getNickname() != null){

                BoardRequestResponseForm responseForm = BoardRequestResponseForm.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .writer(board.getAnonymousWriter())
                        .content(board.getContent())
                        .createDate(board.getCreateDate())
                        .build();
                requestResponseFormList.add(responseForm);

            }else {

                BoardRequestResponseForm responseForm = BoardRequestResponseForm.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .writer(board.getNickname())
                        .content(board.getContent())
                        .createDate(board.getCreateDate())
                        .build();
                requestResponseFormList.add(responseForm);
            }
        }

        return requestResponseFormList;
    }

    @Override
    public BoardRegisterResponseForm register(BoardRegisterRequestForm requestForm, String ticker) {
        BoardRegisterResponseForm responseForm = new BoardRegisterResponseForm();

        Optional<Stock> maybeStock = stockRepository.findByTicker(ticker);

        if (maybeStock.isEmpty()) {
            log.info("no stock");
            return null;
        }

        Stock stock = maybeStock.get();

        if (requestForm.getPassword() == null || !requestForm.getPassword().trim().isEmpty()) {
            Board board = Board.builder()
                    .title(requestForm.getTitle())
                    .anonymousWriter(requestForm.getWriter())
                    .content(requestForm.getContent())
                    .password(requestForm.getPassword())
                    .stock(stock)
                    .build();

            boardRepository.save(board);
            responseForm.setId(board.getId());
            responseForm.setTicker(stock.getTicker());
            return responseForm;

        } else {

            Optional<Account> maybeAccount = accountRepository.findByNicknameWithLazy(requestForm.getNickname());
            if (maybeAccount.isEmpty()) {
                return null;
            }
            Account account = maybeAccount.get();

            Board board = Board.builder()
                    .title(requestForm.getTitle())
                    .nickname(requestForm.getNickname())
                    .account(account)
                    .content(requestForm.getContent())
                    .password(requestForm.getPassword())
                    .stock(stock)
                    .build();

            boardRepository.save(board);
            responseForm.setId(board.getId());
            responseForm.setTicker(stock.getTicker());
            return responseForm;
        }
    }

    @Override
    public BoardRequestResponseForm request(String ticker, Long id) {

        Optional<Board> maybeBoard = boardRepository.findById(id);

        if (maybeBoard.isEmpty()){
            return null;
        }
        Board board = maybeBoard.get();

        if (board.getNickname() == null) {
            BoardRequestResponseForm responseForm = BoardRequestResponseForm.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .writer(board.getAnonymousWriter())
                    .password(board.getPassword())
                    .content(board.getContent())
                    .createDate(board.getCreateDate())
                    .updateDate(board.getUpdateDate())
                    .build();
            return responseForm;

        }else {

            BoardRequestResponseForm responseForm = BoardRequestResponseForm.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .writer(board.getNickname())
                    .content(board.getContent())
                    .createDate(board.getCreateDate())
                    .updateDate(board.getUpdateDate())
                    .build();
            return responseForm;
        }
    }

    @Override
    public BoardRequestResponseForm modify(BoardRegisterRequestForm requestForm, String ticker, Long id) {
        Optional<Board> maybeBoard = boardRepository.findById(id);

        if (maybeBoard.isEmpty()){
            return null;
        }

        Board board = maybeBoard.get();
        board.setTitle(requestForm.getTitle());
        board.setContent(requestForm.getContent());
        board.setUpdateDate(LocalDateTime.now());

        boardRepository.save(board);

        BoardRequestResponseForm responseForm = BoardRequestResponseForm.builder()
                                                                    .id(board.getId())
                                                                    .title(board.getTitle())
                                                                    .content(board.getContent())
                                                                    .createDate(board.getCreateDate())
                                                                    .updateDate(board.getUpdateDate())
                                                                    .build();

        return responseForm;
    }

    @Override
    public void delete(String ticker, Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public void commentRegister(CommentRegisterRequestForm requestForm) {

        if (requestForm.getPassword() == null || requestForm.getPassword().trim().length() == 0) {
            Optional<Account> maybeAccount = accountRepository.findByNicknameWithLazy(requestForm.getNickname());
            if (maybeAccount.isEmpty()) {

                return;
            }
            Account account = maybeAccount.get();

            Optional<Board> maybeBoard = boardRepository.findById(requestForm.getId());
            if (maybeBoard.isEmpty()) {

                return;
            }
            Board board =maybeBoard.get();

            Comment comment = Comment.builder()
                    .account(account)
                    .board(board)
                    .content(requestForm.getContent())
                    .build();

            commentRepository.save(comment);

        } else {

            Optional<Board> maybeBoard = boardRepository.findById(requestForm.getId());

            if (maybeBoard.isEmpty()) {

                return;
            }
            Board board =maybeBoard.get();

            Comment comment = Comment.builder()
                    .board(board)
                    .anonymousWriter(requestForm.getWriter())
                    .password(requestForm.getPassword())
                    .content(requestForm.getContent())
                    .build();

            commentRepository.save(comment);
        }

    }

    @Override
    public List<CommentResponseForm> commentsListResponse(Long id) {

        List<CommentResponseForm> responseForms = new ArrayList<>();

        List<Comment> comments = commentRepository.findByBoardId(id);

        if (comments == null) {
            return null;
        }

        for (Comment comment : comments) {
            if (comment.getPassword() == null) {

                CommentResponseForm responseForm = CommentResponseForm.builder()
                        .id(comment.getId())
                        .writer(comment.getAccount().getAccountNickname().getNickname())
                        .content(comment.getContent())
                        .createDate(comment.getCreateDate())
                        .build();
                responseForms.add(responseForm);
            } else {
                CommentResponseForm responseForm = CommentResponseForm.builder()
                        .id(comment.getId())
                        .writer(comment.getAnonymousWriter())
                        .password(comment.getPassword())
                        .content(comment.getContent())
                        .createDate(comment.getCreateDate())
                        .build();
                responseForms.add(responseForm);
            }
        }

        return responseForms;
    }

    @Override
    public Boolean commentDelete(CommentDeleteRequestForm requestForm) {

        if (requestForm.getDeletePassword() != null && !requestForm.getDeletePassword().trim().isEmpty()) {
            Optional<Comment> maybeComment = commentRepository.findById(requestForm.getSelectedCommentId());
            if (maybeComment.isEmpty()) {
                return false;
            }

            Comment comment = maybeComment.get();
            if (comment.getPassword() != null && comment.getPassword().equals(requestForm.getDeletePassword())) {
                commentRepository.delete(comment);
                return true;

            }
        } else {
            Optional<Comment> maybeComment = commentRepository.findByIdWithLazy(requestForm.getSelectedCommentId());
            if (maybeComment.isEmpty()) {
                return false;
            }
            Comment comment = maybeComment.get();
            if (comment.getAccount().getAccountNickname().getNickname().equals(requestForm.getNickname())) {
                commentRepository.delete(comment);
                return true;
            }

        }
        return false;
    }
}
