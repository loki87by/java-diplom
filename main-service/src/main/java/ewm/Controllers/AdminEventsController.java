package ewm.Controllers;

import ewm.Dtos.EventUpdateResponse;
import ewm.Dtos.FullEventDto;
import ewm.Services.EventService;
import ewm.Errors.EntityNotFoundException;
import ewm.Errors.ForbiddenException;
import ewm.main_service.Utils;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventsController {
    private final EventService service;
    private final Utils utils;

    //ADMIN
    @GetMapping("")
    public List<FullEventDto> getEvents(
            @RequestParam(required = false) List<Object> users,
            @RequestParam(required = false) List<Object> categories,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        List<Long> uIds = null;

        if (users != null && !users.isEmpty()) {
            uIds = utils.massValidate(users);
        }
        List<Long> cIds = null;

        if (categories != null && !categories.isEmpty()) {
            cIds = utils.massValidate(categories);
        }
        return service.findEvents(uIds,
                cIds,
                states,
                rangeStart,
                rangeEnd,
                from,
                size);
    }

    @PatchMapping("/{eventId}")
    public FullEventDto updateEvent(@RequestBody EventUpdateResponse data,
                                    @PathVariable Object eventId) {
        Long id = utils.idValidation(eventId);
        FullEventDto old = service.getEvent(id);

        if (old == null) {
            throw new EntityNotFoundException("Event with id=" + eventId + " was not found");
        }
        Timestamp eventTime = Timestamp.valueOf(data.getEventDate().toLocalDateTime());
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp minimalTime = new Timestamp(currentTimeMillis + 3600000);

        if (eventTime.before(minimalTime)) {
            throw new ForbiddenException("Event time need after now+1hrs");
        }

        if (!old.getState().equals("PENDING") && data.getStateAction().equals("PUBLISH_EVENT")) {
            throw new ForbiddenException("Only pending events can`t be PUBLISH");
        }

        if (!old.getState().equals("PUBLISH") && data.getStateAction().equals("REJECT_EVENT")) {
            throw new ForbiddenException("PUBLISHED events can`t be REJECT");
        }
        return service.updateAndApproveEvent(data, id);
    }

}