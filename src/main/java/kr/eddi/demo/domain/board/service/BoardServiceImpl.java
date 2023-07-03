package kr.eddi.demo.domain.board.service;

import kr.eddi.demo.domain.board.controller.form.BoardModifyRequestForm;
import kr.eddi.demo.domain.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.entity.Board;
import kr.eddi.demo.domain.board.entity.StockBoardList;
import kr.eddi.demo.domain.board.repository.BoardRepository;
import kr.eddi.demo.domain.board.repository.StockBoardListRepository;
import kr.eddi.demo.domain.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    final private BoardRepository boardRepository;
    final private StockBoardListRepository stockBoardListRepository;

    @Override
    public List<Board> list(String ticker) {

        Optional<StockBoardList> maybeStockBoardList = stockBoardListRepository.findByStockTicker(ticker);

        if (maybeStockBoardList.isEmpty()) {
            log.info("게시판이 없습니다");
        }
        List<Board> boardList = maybeStockBoardList.get().getBoardList();

        return boardList;
    }

    @Override
    public Board register(BoardRegisterRequestForm requestForm, String ticker) {

        Board board = boardRepository.save(requestForm.toBoardRegisterRequest().toBoard());

        stockBoardListRepository.findByStockTicker(ticker).get().getBoardList().add(board);

        return null;
    }
}
