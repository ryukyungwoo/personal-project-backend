package kr.eddi.demo.domain.board.controller.form.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRegisterResponseForm {
    private Long id;
    private String ticker;
}