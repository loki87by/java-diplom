package ewm.Entityes;

import jakarta.persistence.*;

import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "requests")
public class EventRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created")
    private Timestamp created;
    @Column(name = "event", nullable = false)
    private Long event;
    @Column(name = "requester", nullable = false)
    private Long requester;
    @Column(name = "status")
    private String status = "PENDING";
}
