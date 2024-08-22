package ewm.Controllers;

import ewm.Dtos.EventDto;
import ewm.Entityes.EventRequest;
import ewm.Dtos.FullEventDto;
import ewm.Dtos.newEventDto;
import ewm.Entityes.RequestConfirmBody;
import ewm.Entityes.RequestConfirmResponse;
import ewm.Services.EventService;
import ewm.Errors.ConflictException;
import ewm.Errors.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventsController {
    private final EventService service;

    //PRIVATE
    @GetMapping("")
    public List<EventDto> getEvents(
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Long userId) {
        return service.getEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public FullEventDto getEvent(
            @PathVariable Long userId, @PathVariable Long eventId) {
        //ToDo: check type and return exception
        return service.getEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public EventRequest getEventRequest(
            @PathVariable Long userId, @PathVariable Long eventId) {
        return service.getRequest(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public RequestConfirmResponse confirmEventRequest(
            @PathVariable Long userId, @PathVariable Long eventId, @RequestBody RequestConfirmBody body) {
        FullEventDto event = service.getEvent(userId, eventId);
        int limit = event.getParticipantLimit();
        int confirmedCount = event.getConfirmedRequests();

        if (confirmedCount >= limit) {
            throw new ConflictException("The participant limit has been reached");
        }
        return service.confirmEventRequest(userId, eventId, body);
    }

    @PatchMapping("/{eventId}")
    public FullEventDto updateEvent(
            @RequestBody FullEventDto dto, @PathVariable Long userId, @PathVariable Long eventId) {
        FullEventDto old = service.getEvent(userId, eventId);
        String state = old.getState();
        Timestamp eventTime = Timestamp.valueOf(dto.getEventDate());
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp minimalTime = new Timestamp(currentTimeMillis + 7200000);

        if (!state.equals("CANCEL_REVIEW") && !state.equals("PENDING")) {
            throw new ForbiddenException("Only pending or canceled events can be changed");
        }

        if (eventTime.before(minimalTime)) {
            throw new ForbiddenException("Time need after now+2hrs");
        }
        return service.updateEvent(userId, dto, eventId);
    }

    @PostMapping("")
    public FullEventDto setEvent(
            @RequestBody newEventDto dto, @PathVariable Long userId) {
        Timestamp eventTime = Timestamp.valueOf(dto.getEventDate().toLocalDateTime());
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp minimalTime = new Timestamp(currentTimeMillis + 7200000);

        if (eventTime.before(minimalTime)) {
            throw new ForbiddenException("Time need after now+2hrs");
        }
        return service.setEvent(userId, dto);
    }

}