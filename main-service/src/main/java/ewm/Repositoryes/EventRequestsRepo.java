package ewm.Repositoryes;

import ewm.Objects.EventRequest;
import ewm.Utils.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRequestsRepo {
    private final EventReqJPARepository jpa;

    public EventRequest findByEventId(Long eventId) {
        return jpa.findByEvent(eventId).orElse(null);
    }

    public EventRequest findById(Long id) {
        EventRequest req = jpa.findById(id).orElse(null);
        if (req == null) {
            throw new EntityNotFoundException("Запрос с id="+id+" не найден.");
        }
        return req;
    }

    public List<EventRequest> findByUserId(Long id) {
        return jpa.findByRequester(id);
    }

    public String findStatusById(Long id) {
        return findById(id).getStatus();
    }

    public EventRequest findRequest(Long userId, Long eventId) {
        return jpa.findByRequesterAndEvent(userId, eventId).orElse(null);
    }

    public EventRequest changeStatus(Long id, String status) {
        EventRequest req = findById(id);
        req.setStatus(status);
        return jpa.saveAndFlush(req);
    }

    public EventRequest addRequest(EventRequest req) {
        return jpa.saveAndFlush(req);
    }
}
