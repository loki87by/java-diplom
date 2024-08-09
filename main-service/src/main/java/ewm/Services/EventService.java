package ewm.Services;

import ewm.Dtos.EventDto;
import ewm.Dtos.EventUpdateResponse;
import ewm.Dtos.FullEventDto;
import ewm.Dtos.newEventDto;
import ewm.Mappers.EventMapper;
import ewm.Objects.*;
import ewm.Repositoryes.EventRequestsRepo;
import ewm.Repositoryes.EventsRepo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventsRepo eRepo;
    private final EventRequestsRepo rRepo;
    private final EventMapper mapper;

    public List<EventDto> getEvents(Long userId, int from, int size) {
        List<EventDto> events = new ArrayList<>();
        List<Event> eventsFromDb = eRepo.getById(userId, from, size);

        if (eventsFromDb != null) {
            for (Event event : eventsFromDb) {
                events.add(mapper.toObject(event));
            }
        }
        return events;
    }

    public FullEventDto getEvent(Long userId, Long eventId) throws ClassNotFoundException {
        Event result = eRepo.getEvent(userId, eventId);
        if (result == null) {
            throw new ClassNotFoundException();
        }
        return mapper.toFullDto(result);
    }

    public FullEventDto getEvent(Long eventId) {
        return mapper.toFullDto(eRepo.getEvent(eventId));
    }

    public FullEventDto setEvent(Long userId, newEventDto dto) {
        Event event = mapper.toEvent(userId, dto);
        Event result = eRepo.setEvent(event);
        return mapper.toFullDto(result);
    }

    public FullEventDto updateEvent(Long userId, FullEventDto dto, Long eventId) throws IllegalAccessException, ClassNotFoundException {
        FullEventDto oldDto = getEvent(userId, eventId);
        Class<?> objectClass = FullEventDto.class;
        Field[] fields = objectClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(dto);

            if (value != null) {
                field.set(oldDto, value);
            }
        }
        Event updatedEvent = eRepo.setEvent(mapper.toEvent(userId, oldDto));
        return mapper.toFullDto(updatedEvent);
    }

    public EventRequest getRequest(Long userId, Long eventId) throws BadRequestException {
        Event event = eRepo.getEvent(userId, eventId);
        if (event == null) {
            throw new BadRequestException();
        }
        return rRepo.findByEventId(eventId);
    }

    private boolean checkStatus(Long id) throws ClassNotFoundException {
        return rRepo.findStatusById(id).equals("PENDING");
    }

    public RequestConfirmResponse confirmEventRequest(Long userId, Long eventId, RequestConfirmBody body)
            throws ClassNotFoundException, BadRequestException {
        Event event = eRepo.getEvent(userId, eventId);
        int limit = event.getParticipantLimit();
        int confirmedCount = event.getConfirmedRequests();
        List<Long> reqIds = body.getRequestIds();

        for (Long id : reqIds) {
            if (!checkStatus(id)) {
                throw new IllegalArgumentException();
            }
        }
        List<EventRequest> confirmedRequests = new ArrayList<>();
        List<EventRequest> rejectedRequests = new ArrayList<>();

        for (Long id : reqIds) {

            if (body.getStatus().equals("REJECTED") || confirmedCount >= limit) {
                EventRequest req = rRepo.changeStatus(id, "REJECTED");
                rejectedRequests.add(req);
            } else if (body.getStatus().equals("CONFIRMED")) {
                confirmedCount++;
                event.setConfirmedRequests(confirmedCount);
                EventRequest updated = rRepo.changeStatus(id, body.getStatus());
                confirmedRequests.add(updated);
            } else {
                throw new BadRequestException("unknown status");
            }
        }
        RequestConfirmResponse res = new RequestConfirmResponse();

        if (!confirmedRequests.isEmpty()) {
            res.setConfirmedRequests(confirmedRequests);
        }

        if (!rejectedRequests.isEmpty()) {
            res.setRejectedRequests(rejectedRequests);
        }

        return res;
    }

    public List<FullEventDto> findEvents(List<Long> users,
                                         List<Long> categories,
                                         List<String> states,
                                         String rangeStart,
                                         String rangeEnd,
                                         int from,
                                         int size) {
        List<Event> events = eRepo.findEvents(users,
                categories,
                states,
                rangeStart,
                rangeEnd,
                from,
                size);
        List<FullEventDto> fulls = new ArrayList<>();
        for (Event event: events) {
            fulls.add(mapper.toFullDto(event));
        }
        return fulls;
    }

    public FullEventDto updateAndApproveEvent(EventUpdateResponse data, Long eventId) throws IllegalAccessException {
        Event oldDto = eRepo.getEvent(eventId);
        Class<?> objectClass = EventUpdateResponse.class;
        Field[] fields = objectClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(data);

            if (value != null) {
                try {
                    Field correspondingField = oldDto.getClass().getDeclaredField(field.getName());
                    correspondingField.setAccessible(true);
                    correspondingField.set(oldDto, value);
                } catch (NoSuchFieldException e) {
                    if(field.getName().equals("stateAction")){
                        if(value.equals("PUBLISH_EVENT")) {
                            oldDto.setState("PUBLISHED");
                        } else if(value.equals("REJECT_EVENT")) {
                            oldDto.setState("REJECTED");
                        }
                    }
                }
            }
        }
        Event updatedEvent = eRepo.setEvent(oldDto);
        return mapper.toFullDto(updatedEvent);
    }

    public List<EventDto> getEventsByParams(String text,
                                            List<Long> catsIds,
                                            Boolean paid,
                                            String rangeStart,
                                            String rangeEnd,
                                            Boolean onlyAvailable,
                                            String sort,
                                            int from,
                                            int size) {
        List<Event> events = eRepo.findEvents(
                text,
                catsIds,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size);
        List<EventDto> fulls = new ArrayList<>();
        for (Event event: events) {
            fulls.add(mapper.toObject(event));
        }
        return fulls;
    }
}
