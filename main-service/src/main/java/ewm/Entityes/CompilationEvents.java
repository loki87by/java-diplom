package ewm.Entityes;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "compilation_events")
public class CompilationEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "compilation_id", nullable = false)
    Long compilationId;
    @Column(name = "event_id", nullable = false)
    Long eventId;

    public CompilationEvents(Long eId, Long cId) {
        this.compilationId = cId;
        this.eventId = eId;
    }

    public CompilationEvents() {

    }
}
