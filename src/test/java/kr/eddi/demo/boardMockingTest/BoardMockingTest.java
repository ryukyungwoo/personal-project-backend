//package kr.eddi.demo.boardMockingTest;
//
//import kr.eddi.demo.domain.board.controller.form.request.BoardRegisterRequestForm;
//import kr.eddi.demo.domain.board.controller.form.response.BoardRegisterResponseForm;
//import kr.eddi.demo.domain.board.entity.Board;
//import kr.eddi.demo.domain.board.repository.BoardRepository;
//import kr.eddi.demo.domain.board.repository.StockBoardListRepository;
//import kr.eddi.demo.domain.board.service.BoardServiceImpl;
//import kr.eddi.demo.domain.stock.repository.StockRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest
//public class BoardMockingTest {
//    @Mock
//    private BoardRepository mockBoardRepository;
//    @Mock
//    private StockRepository mockStockRepository;
//    @Mock
//    private StockBoardListRepository mockStockBoardListRepository;
//    @InjectMocks
//    private BoardServiceImpl mockService;
//
//    @BeforeEach
//    public void setup () throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    @DisplayName("BoardMocking Test: 게시물 등록")
//    public void 게시물을_등록합니다 () {
//
//        final String ticker = "005930";
//        final BoardRegisterRequestForm requestForm = new BoardRegisterRequestForm("타이틀", "내용", "작성자");
//
//        final Board board = requestForm.toBoardRegisterRequest().toBoard();
//        when(mockBoardRepository.save(board)).thenReturn(new Board("제목", "작성자", "내용"));
//
//        final BoardServiceImpl sut = new BoardServiceImpl(mockBoardRepository, mockStockRepository, mockStockBoardListRepository);
//        final BoardRegisterResponseForm actual = sut.register(requestForm, ticker);
//
//        assertEquals(actual.getId(), board.getId());
//    }
//}
