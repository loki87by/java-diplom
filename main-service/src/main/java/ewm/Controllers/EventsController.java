package ewm.Controllers;

import ewm.Dtos.EventDto;
import ewm.Dtos.FullEventDto;
import ewm.Services.EventService;
import ewm.Utils.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventsController {
    private final EventService service;

    //PUBLIC
    @GetMapping("/")
    //TODO: add to statistic
    public List<EventDto> getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> cats,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
        @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        return service.getEventsByParams(text,
                cats,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size);
    }

    @GetMapping("/{id}")
    //TODO: add to statistic
    public FullEventDto getEvent(@PathVariable Long id) {
        FullEventDto event = service.getEvent(id);
        //ToDo: check id type and return exception

        if(event == null) {
            throw new EntityNotFoundException("Event with id="+id+" was not found");
        }

        if(event.getState().equals("PUBLISHED")) {
            return event;
        } else {
            throw new EntityNotFoundException("Published event with id="+id+" was not found");
        }
    }
}