package kr.eddi.demo.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @OneToMany(mappedBy = "boardList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    final private List<Board> boards = new ArrayList<>();

}
