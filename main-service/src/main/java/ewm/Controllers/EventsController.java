package ewm.Controllers;

import ewm.Dtos.EventDto;
import ewm.Dtos.FullEventDto;
import ewm.Entityes.Log;
import ewm.Services.EventService;
import ewm.Errors.EntityNotFoundException;
import ewm.main_service.SendLog;
import ewm.main_service.Utils;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventsController {
    private final EventService service;
    private final Utils utils;

    //PUBLIC
    @GetMapping("")
    public List<EventDto> getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Object> cats,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest req) {
        String ip = req.getRemoteAddr();
        String uri = req.getRequestURI();
        Log log = new Log(uri, ip);
        SendLog logger = new SendLog();
        logger.sendPost(log);
        List<Long> catIds = null;

        if (cats != null && !cats.isEmpty()) {
            catIds = utils.massValidate(cats);
        }
        return service.getEventsByParams(text,
                catIds,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size);
    }

    @GetMapping("/{eventId}")
    public FullEventDto getEvent(@PathVariable Long eventId, HttpServletRequest req) {
        String ip = req.getRemoteAddr();
        String uri = req.getRequestURI();
        Log log = new Log(uri, ip);
        SendLog logger = new SendLog();
        logger.sendPost(log);
        Long id = utils.idValidation(eventId);
        FullEventDto event = service.getEvent(id);

        if (event == null) {
            throw new EntityNotFoundException("Event with id=" + id + " was not found");
        }

        if (event.getState().equals("PUBLISHED")) {
            return event;
        } else {
            throw new EntityNotFoundException("Published event with id=" + id + " was not found");
        }
    }
}