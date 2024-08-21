package ewm.Entityes;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "coordinates")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "lat", nullable = false)
    double lat;
    @Column(name = "lon", nullable = false)
    double lon;
}
