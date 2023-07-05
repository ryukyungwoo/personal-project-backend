package kr.eddi.demo.domain.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @OneToMany(mappedBy = "boardList", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Board> boards =  new ArrayList<>();

}
