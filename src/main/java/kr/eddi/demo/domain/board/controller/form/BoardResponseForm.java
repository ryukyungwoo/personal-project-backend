package kr.eddi.demo.domain.board.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseForm {
    private Integer orderNumber;
    private String ticker;
}