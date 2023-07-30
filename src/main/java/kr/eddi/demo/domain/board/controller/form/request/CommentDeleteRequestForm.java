package kr.eddi.demo.domain.board.controller.form.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDeleteRequestForm {
    private Long selectedCommentId;
    private String deletePassword;
    private String nickname;
}
