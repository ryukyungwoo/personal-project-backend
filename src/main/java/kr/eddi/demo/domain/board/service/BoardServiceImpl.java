package kr.eddi.demo.domain.board.service;

import kr.eddi.demo.domain.board.controller.form.request.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.controller.form.response.BoardRequestResponseForm;
import kr.eddi.demo.domain.board.controller.form.response.BoardRegisterResponseForm;
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
    public List<BoardRequestResponseForm> list(String ticker) {

        StockBoardList stockBoardList = findByTicker(ticker);
        if(stockBoardList == null) {
            return null;
        }
        List<Board> boards = stockBoardList.getBoards();

        List<BoardRequestResponseForm> responseForms = new ArrayList<>();
        for (Board board : boards) {
            BoardRequestResponseForm responseForm = new BoardRequestResponseForm(
                    board.getId(),
                    board.getTitle(),
                    board.getWriter(),
                    board.getContent(),
                    board.getCreateDate()
            );
            responseForms.add(responseForm);
        }

        return responseForms;
    }

    @Override
    public BoardRegisterResponseForm register(BoardRegisterRequestForm requestForm, String ticker) {

        Optional<StockBoardList> maybeStockBoardList = stockBoardListRepository.findByStockTicker(ticker);

        if (maybeStockBoardList.isPresent()) {
            StockBoardList stockBoardList = maybeStockBoardList.get();
            Board board = requestForm.toBoardRegisterRequest().toBoard();
            board.setStockBoardList(stockBoardList);
            stockBoardList.getBoards().add(board);
            boardRepository.save(board);

            BoardRegisterResponseForm responseForm = new BoardRegisterResponseForm();
            responseForm.setId(board.getId());
            responseForm.setTicker(ticker);
            return responseForm;
        }

        Board board = requestForm.toBoardRegisterRequest().toBoard();

        Optional<Stock> maybeStock = stockRepository.findByTicker(ticker);
        if (maybeStock.isEmpty()) {
            log.debug("Stock not found");
        }
        Stock stock = maybeStock.get();

        StockBoardList stockBoardList = new StockBoardList(stock, new ArrayList<>());
        stockBoardListRepository.save(stockBoardList);

        board.setStockBoardList(stockBoardList);
        boardRepository.save(board);

        BoardRegisterResponseForm responseForm = new BoardRegisterResponseForm();

        responseForm.setId(board.getId());
        responseForm.setTicker(ticker);

        log.debug("id: " + responseForm.getId());
        log.debug("ticker" + responseForm.getTicker());

        return responseForm;
    }

    @Override
    public BoardRequestResponseForm request(String ticker, Long id) {

        StockBoardList stockBoardList = findByTicker(ticker);
        if(stockBoardList == null) {
            return null;
        }
            Board board = boardRepository.findById(id).get();

           BoardRequestResponseForm responseForm = new BoardRequestResponseForm();
           responseForm.setId(board.getId());
           responseForm.setTitle(board.getTitle());
           responseForm.setWriter(board.getWriter());
           responseForm.setContent(board.getContent());
           responseForm.setUpdateDate(board.getCreateDate());

            return responseForm;
    }

    @Override
    public BoardRequestResponseForm modify(BoardRegisterRequestForm requestForm, String ticker, Long id) {

        StockBoardList stockBoardList = findByTicker(ticker);
        if(stockBoardList == null) {
            return null;
        }
        Board board = boardRepository.findById(id).get();

        board.setTitle(requestForm.getTitle());
        board.setContent(requestForm.getContent());
        board.setCreateDate(requestForm.toBoardRegisterRequest().toBoard().getCreateDate());

        boardRepository.save(board);

        BoardRequestResponseForm responseForm = new BoardRequestResponseForm();
        responseForm.setId(board.getId());
        responseForm.setTitle(board.getTitle());
        responseForm.setWriter(board.getWriter());
        responseForm.setContent(board.getContent());
        responseForm.setUpdateDate(board.getCreateDate());

        return responseForm;
    }

    @Override
    public void delete(String ticker, Long id) {
        boardRepository.deleteById(id);
    }

    public StockBoardList findByTicker (String ticker) {
        Optional<Stock> maybeStock = stockRepository.findByTicker(ticker);
        if (maybeStock.isEmpty()) {
            log.debug("Stock not found");
        }
        Stock stock = maybeStock.get();

        Optional<StockBoardList> maybeStockBoardList = stockBoardListRepository.findByStockTicker(stock.getTicker());
        if (maybeStockBoardList.isEmpty()){
            log.debug("no stockBoardList");
            return null;
        }
        return maybeStockBoardList.get();
    }
}
