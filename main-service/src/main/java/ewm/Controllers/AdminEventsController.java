package ewm.Controllers;

import ewm.Dtos.EventUpdateResponse;
import ewm.Dtos.FullEventDto;
import ewm.Services.EventService;
import ewm.Utils.EntityNotFoundException;
import ewm.Utils.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventsController {
    private final EventService service;

    //ADMIN
    @GetMapping("/")
    public List<FullEventDto> getEvents(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        return service.findEvents(users,
                categories,
                states,
                rangeStart,
                rangeEnd,
                from,
                size);
    }

    @PatchMapping("/{eventId}")
    public FullEventDto updateEvent(@RequestBody EventUpdateResponse data,
            @PathVariable Long eventId) {
        FullEventDto old = service.getEvent(eventId);

        if (old == null) {
            throw new EntityNotFoundException("Event with id="+eventId+" was not found");
        }
        Timestamp eventTime = old.getPublishedOn();
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp minimalTime = new Timestamp(currentTimeMillis + 3600000);

        if (eventTime.before(minimalTime)) {
            throw new ForbiddenException("Publication time need after now+1hrs");
        }

        if (!old.getState().equals("PENDING") && data.getState().equals("PUBLISH_EVENT")) {
            throw new ForbiddenException("Only pending events can`t be PUBLISH");
        }

        if (!old.getState().equals("PUBLISH") && data.getState().equals("REJECT_EVENT")) {
            throw new ForbiddenException("PUBLISHED events can`t be REJECT");
        }
        return service.updateAndApproveEvent(data, eventId);
    }

}