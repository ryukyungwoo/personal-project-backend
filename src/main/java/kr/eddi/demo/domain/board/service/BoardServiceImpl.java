package kr.eddi.demo.domain.board.service;

import kr.eddi.demo.domain.board.controller.form.BoardRegisterRequestForm;
import kr.eddi.demo.domain.board.entity.Board;
import kr.eddi.demo.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    final private BoardRepository boardRepository;
    @Override
    public List<Board> list() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Board register(BoardRegisterRequestForm requestForm) {
        return boardRepository.save(requestForm.toBoardRegisterRequest().toBoard());
    }

    @Override
    public Board read(Long id) {
        Optional<Board> maybeBoard = boardRepository.findById(id);

        if (maybeBoard.isEmpty()) {
            log.info("게시글이 없습니다");
            return null;
        }

        return maybeBoard.get();
    }
}
