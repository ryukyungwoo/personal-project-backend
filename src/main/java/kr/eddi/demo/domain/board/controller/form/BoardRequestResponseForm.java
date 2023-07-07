package kr.eddi.demo.domain.board.controller.form;

import jakarta.persistence.*;
import kr.eddi.demo.domain.board.entity.StockBoardList;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class BoardRequestResponseForm {
    private String title;
    private String writer;
    private String content;

}