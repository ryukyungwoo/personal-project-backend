package kr.eddi.demo.domain.board.controller.form.request;

//import kr.eddi.demo.domain.board.service.request.BoardRegisterRequest;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardRegisterRequestForm {
    private String title;
    private String content;
    private String writer;
    private String nickname;
    private String password;
}