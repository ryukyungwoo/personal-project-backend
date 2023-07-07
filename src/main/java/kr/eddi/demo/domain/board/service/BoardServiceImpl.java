package kr.eddi.demo.domain.board.service;

import kr.eddi.demo.domain.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.controller.form.BoardRequestResponseForm;
import kr.eddi.demo.domain.board.controller.form.BoardResponseForm;
import kr.eddi.demo.domain.board.entity.Board;
//import kr.eddi.demo.domain.board.entity.StockBoardList;
import kr.eddi.demo.domain.board.entity.StockBoardList;
import kr.eddi.demo.domain.board.repository.BoardRepository;
//import kr.eddi.demo.domain.board.repository.StockBoardListRepository;
import kr.eddi.demo.domain.board.repository.StockBoardListRepository;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    final private BoardRepository boardRepository;
    final private StockRepository stockRepository;
    final private StockBoardListRepository stockBoardListRepository;

    @Override
    public List<Board> list(String ticker) {
        return null;
    }

    @Override
    public BoardResponseForm register(BoardRegisterRequestForm requestForm, String ticker) {

        Board board = requestForm.toBoardRegisterRequest().toBoard();
        Stock stock = stockRepository.findByTicker(ticker).get();
        StockBoardList stockBoardList = new StockBoardList(stock, new ArrayList<>());

        board.setStockBoardList(stockBoardList);

        boardRepository.save(board);
        stockBoardListRepository.save(stockBoardList);
        BoardResponseForm responseForm = new BoardResponseForm();

        responseForm.setOrderNumber(1);
        responseForm.setTicker(ticker);

        log.info("OrderNum: " + responseForm.getOrderNumber());
        log.info("ticker" + responseForm.getTicker());

        return responseForm;
    }

    @Override
    public BoardRequestResponseForm request(String ticker, Integer orderNumber) {
        Stock stock = stockRepository.findByTicker(ticker).get();
        Optional<StockBoardList> maybeStockBoardList = stockBoardListRepository.findByStockTicker(stock.getTicker());

           if (maybeStockBoardList.isEmpty()){
               log.info("no stockBoardList");
               return null;
           }

           Board board =  maybeStockBoardList.get().getBoards().get((orderNumber - 1));
           log.info("board: " + board);

           BoardRequestResponseForm responseForm = new BoardRequestResponseForm();
           responseForm.setTitle(board.getTitle());
           responseForm.setWriter(board.getWriter());
           responseForm.setContent(board.getContent());

            return responseForm;
    }

    @Override
    public void modify(BoardRegisterRequestForm requestForm, String ticker, Long id) {

    }

    @Override
    public void delete(String ticker, Long id) {

    }
}
