package kr.eddi.demo.domain.board.controller.form.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardRequestResponseForm {
    private Long id;
    private String title;
    private String writer;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    public BoardRequestResponseForm(Long id, String title, String writer, String content, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.updateDate = updateDate;
    }
}