package ewm.Entityes;

import ewm.Dtos.EventCompDto;

import jakarta.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    String title;
    @Column(name = "pinned")
    Boolean pinned;
    @Transient
    List<EventCompDto> events = new ArrayList<>();
}
