package ewm.Repositoryes;

import ewm.Entityes.Event;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface EventJPARepository extends JpaRepository<Event, Long> {
    Optional<List<Event>> findByCategoryId(Long id);

    Page<Event> findAllByUserId(Long userId, Pageable pageable);

    Optional<Event> findByIdAndUserId(Long userId, Long id);

    List<Event> findAllByCategoryId(Long id);

    List<Event> findAll(Specification<Event> spec, Pageable pageable);
}
