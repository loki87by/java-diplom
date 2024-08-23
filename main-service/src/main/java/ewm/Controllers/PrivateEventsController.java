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
import ewm.main_service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventsController {
    private final EventService service;
    private final Utils utils;

    //PRIVATE
    @GetMapping("")
    public List<EventDto> getEvents(
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Object userId) {
        Long id = utils.idValidation(userId);
        return service.getEvents(id, from, size);
    }

    @GetMapping("/{eventId}")
    public FullEventDto getEvent(
            @PathVariable Object userId, @PathVariable Object eventId) {
        Long uId = utils.idValidation(userId);
        Long eId = utils.idValidation(eventId);
        return service.getEvent(uId, eId);
    }

    @GetMapping("/{eventId}/requests")
    public EventRequest getEventRequest(
            @PathVariable Object userId, @PathVariable Object eventId) {
        Long uId = utils.idValidation(userId);
        Long eId = utils.idValidation(eventId);
        return service.getRequest(uId, eId);
    }

    @PatchMapping("/{eventId}/requests")
    public RequestConfirmResponse confirmEventRequest(
            @PathVariable Object userId, @PathVariable Object eventId, @RequestBody RequestConfirmBody body) {
        Long uId = utils.idValidation(userId);
        Long eId = utils.idValidation(eventId);
        FullEventDto event = service.getEvent(uId, eId);
        int limit = event.getParticipantLimit();
        int confirmedCount = event.getConfirmedRequests();

        if (confirmedCount >= limit) {
            throw new ConflictException("The participant limit has been reached");
        }
        return service.confirmEventRequest(uId, eId, body);
    }

    @PatchMapping("/{eventId}")
    public FullEventDto updateEvent(
            @RequestBody FullEventDto dto, @PathVariable Object userId, @PathVariable Object eventId) {
        Long uId = utils.idValidation(userId);
        Long eId = utils.idValidation(eventId);
        FullEventDto old = service.getEvent(uId, eId);
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
        return service.updateEvent(uId, dto, eId);
    }

    @PostMapping("")
    public FullEventDto setEvent(
            @RequestBody newEventDto dto, @PathVariable Object userId) {
        Long uId = utils.idValidation(userId);
        Timestamp eventTime = Timestamp.valueOf(dto.getEventDate());
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp minimalTime = new Timestamp(currentTimeMillis + 7200000);

        if (eventTime.before(minimalTime)) {
            throw new ForbiddenException("Time need after now+2hrs");
        }
        return service.setEvent(uId, dto);
    }

}