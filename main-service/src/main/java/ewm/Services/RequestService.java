package ewm.Services;

import ewm.Entityes.Event;
import ewm.Entityes.EventRequest;
import ewm.Errors.ValidationException;
import ewm.Repositoryes.EventRequestsRepo;
import ewm.Repositoryes.EventsRepo;
import ewm.Errors.EntityNotFoundException;

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

        if (event == null) {
            throw new EntityNotFoundException("Событие с id=" + eventId + " не найдено.");
        }
        System.out.println(Objects.equals((event.getUserId() - userId), 0L));
        return Objects.equals((event.getUserId() - userId), 0L);
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

    public EventRequest cancelRequest(Long userId, Long requestId) {
        EventRequest req = rRepo.findById(requestId);

        if (req == null) {
            throw new EntityNotFoundException("Your request with id=" + requestId + " was not found.");
        }
        Long uId = req.getRequester();

        if (!Objects.equals(uId, userId)) {
            throw new ValidationException("Not owner");
        }
        req.setStatus("PENDING");
        return rRepo.addRequest(req);
    }
}
