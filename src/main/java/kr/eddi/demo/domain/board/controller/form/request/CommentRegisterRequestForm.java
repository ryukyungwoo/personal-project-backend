package kr.eddi.demo.domain.board.controller.form.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentRegisterRequestForm {

    private Long id;

    private String writer;

    private String nickname;

    private String content;

    private String password;
}
