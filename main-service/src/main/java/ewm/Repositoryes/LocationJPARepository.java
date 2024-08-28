package ewm.Repositoryes;

import ewm.Entityes.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface LocationJPARepository extends JpaRepository<Location, Long> {
    Optional<Location> findLocationByLatAndLon(double lat, double lon);
}
