package ewm.Repositoryes;

import ewm.Entityes.EventRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface EventReqJPARepository extends JpaRepository<EventRequest, Long> {
    Optional<EventRequest> findByEvent(Long eventId);

    Optional<EventRequest> findByRequesterAndEvent(Long user, Long event);

    List<EventRequest> findByRequester(Long userId);
}
