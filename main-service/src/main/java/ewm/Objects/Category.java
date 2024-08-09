package ewm.Objects;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categoryes")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name", nullable = false)
    String name;
}
