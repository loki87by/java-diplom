package ewm.Services;

import ewm.Objects.Event;
import ewm.Objects.EventRequest;
import ewm.Repositoryes.EventRequestsRepo;
import ewm.Repositoryes.EventsRepo;
import ewm.Utils.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final EventRequestsRepo rRepo;
    private final EventsRepo eRepo;

    public List<EventRequest> getRequests(Long userId) {
        return rRepo.findByUserId(userId);
    }

    public EventRequest findRequest(Long userId, Long eventId) {
        return rRepo.findRequest(userId, eventId);
    }

    public boolean checkOwner(Long userId, Long eventId) {
        Event event = eRepo.getEvent(eventId);
        return Objects.equals(userId, event.getUserId());
    }

    public boolean checkPublished(Long eventId) {
        Event event = eRepo.getEvent(eventId);
        return Objects.equals(event.getState(), "PUBLISHED");
    }

    public boolean checkLimit(Long eventId) {
        Event event = eRepo.getEvent(eventId);
        return event.getConfirmedRequests() < event.getParticipantLimit();
    }

    public EventRequest setRequest(Long userId, Long eventId) {
        Event event = eRepo.getEvent(eventId);
        boolean moder = event.isRequestModeration();
        EventRequest req = new EventRequest();
        req.setRequester(userId);
        req.setEvent(eventId);
        req.setCreated(Timestamp.from(Instant.now()));

        if (moder) {
            req.setStatus("PENDING");
        } else {
            req.setStatus("CONFIRMED");
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eRepo.setEvent(event);
        }
        return rRepo.addRequest(req);
    }

    public EventRequest cancelRequest(Long userId, Long eventId) {
        EventRequest req = rRepo.findRequest(userId, eventId);

        if(req == null) {
            throw new EntityNotFoundException("Event with id="+eventId+" was not found");
        }
        req.setStatus("PENDING");
        return rRepo.addRequest(req);
    }
}
