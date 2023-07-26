package kr.eddi.demo.domain.board.service;

import kr.eddi.demo.domain.board.controller.form.request.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.controller.form.response.BoardRegisterResponseForm;
import kr.eddi.demo.domain.board.controller.form.response.BoardRequestResponseForm;
import kr.eddi.demo.domain.board.entity.Board;
import kr.eddi.demo.domain.board.repository.BoardRepository;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public List<BoardRequestResponseForm> list(String ticker) {
        Optional<List<Board>> maybeBoardList = boardRepository.findByStockTicker(ticker);

        if (maybeBoardList.isEmpty()) {
            return null;
        }
        List<Board> boardList = maybeBoardList.get();
        List<BoardRequestResponseForm> requestResponseFormList = new ArrayList<>();
        for (Board board : boardList) {
            BoardRequestResponseForm responseForm = BoardRequestResponseForm.builder()
                                                                            .id(board.getId())
                                                                            .title(board.getTitle())
                                                                            .writer(board.getWriter())
                                                                            .content(board.getContent())
                                                                            .createDate(board.getCreateDate())
                                                                            .updateDate(board.getUpdateDate())
                                                                            .build();
            requestResponseFormList.add(responseForm);
        }

        return requestResponseFormList;
    }

    @Override
    public BoardRegisterResponseForm register(BoardRegisterRequestForm requestForm, String ticker) {
        Optional<Stock> maybeStock = stockRepository.findByTicker(ticker);

        if (maybeStock.isEmpty()) {
            log.info("no stock");
            return null;
        }

        Stock stock = maybeStock.get();

        Board board = Board.builder()
                            .title(requestForm.getTitle())
                            .writer(requestForm.getWriter())
                            .content(requestForm.getContent())
                            .stock(stock)
                            .build();

        boardRepository.save(board);

        return null;
    }

    @Override
    public BoardRequestResponseForm request(String ticker, Long id) {
        Optional<Board> maybeBoard = boardRepository.findById(id);

        if (maybeBoard.isEmpty()){
            return null;
        }
        Board board = maybeBoard.get();
        BoardRequestResponseForm responseForm = BoardRequestResponseForm.builder()
                                                                    .id(board.getId())
                                                                    .title(board.getTitle())
                                                                    .writer(board.getWriter())
                                                                    .content(board.getContent())
                                                                    .createDate(board.getCreateDate())
                                                                    .updateDate(board.getUpdateDate())
                                                                    .build();
        return responseForm;
    }

    @Override
    public BoardRequestResponseForm modify(BoardRegisterRequestForm requestForm, String ticker, Long id) {
        Optional<Board> maybeBoard = boardRepository.findById(id);

        if (maybeBoard.isEmpty()){
            return null;
        }
        Board board = maybeBoard.get();
        board.setTitle(requestForm.getTitle());
        board.setWriter(requestForm.getWriter());
        board.setContent(requestForm.getContent());

        boardRepository.save(board);

        BoardRequestResponseForm responseForm = BoardRequestResponseForm.builder()
                                                                    .id(board.getId())
                                                                    .title(board.getTitle())
                                                                    .writer(board.getWriter())
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
}
