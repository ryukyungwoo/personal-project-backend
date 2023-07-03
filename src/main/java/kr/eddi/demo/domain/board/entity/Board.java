package kr.eddi.demo.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;
    private String writer;
    @Setter
    private String content;

    public Board(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }
}
