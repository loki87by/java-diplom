package ewm.Mappers;

import ewm.Dtos.*;
import ewm.Entityes.*;
import ewm.Repositoryes.CategoryRepo;
import ewm.Repositoryes.EventsRepo;
import ewm.Repositoryes.LocationRepo;
import ewm.Repositoryes.UserRepo;
import ewm.main_service.Utils;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final LocationRepo locationRepo;
    private final EventsRepo eRepo;
    private final Utils utils;

    public EventDto toObject(Event event) {
        return new EventDto(
                event.getAnnotation(),
                categoryRepo.findById(event.getCategoryId()),
                event.getConfirmedRequests(),
                event.getEventDate(),
                userRepo.findById(event.getUserId()),
                event.getPaid(),
                event.getTitle(),
                event.getViews()
        );
    }

    public Event toEvent(Long userId, newEventDto dto) {
        Location location = locationRepo.setLocation(dto.getLocation());
        Event event = new Event();
        event.setAnnotation(dto.getAnnotation());
        event.setCategoryId(dto.getCategory());
        event.setDescription(dto.getDescription());
        event.setEventDate(utils.stringToTimestamp(dto.getEventDate(), false));
        event.setLocationId(location.getId());
        event.setPaid(dto.getPaid());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setRequestModeration(dto.isRequestModeration());
        event.setTitle(dto.getTitle());
        event.setUserId(userId);
        return event;
    }

    public Event toEvent(Long userId, FullEventDto dto) {
        Long compilationId = eRepo.getEvent(userId, dto.getId()).getCompilationId();
        Location location = locationRepo.findByData(dto.getLocation().getLat(), dto.getLocation().getLon()).orElse(null);
        Long locationId = null;

        if (location != null) {
            locationId = location.getId();
        }
        Event event = new Event();
        event.setId(dto.getId());
        event.setAnnotation(dto.getAnnotation());
        event.setCategoryId(dto.getCategory().getId());
        event.setConfirmedRequests(dto.getConfirmedRequests());
        event.setEventDate(utils.stringToTimestamp(dto.getEventDate(), false));
        event.setUserId(userId);
        event.setPaid(dto.getPaid());
        event.setTitle(dto.getTitle());
        event.setViews(dto.getViews());
        event.setCompilationId(compilationId);
        event.setCreatedOn(utils.stringToTimestamp(dto.getEventDate(), false));
        event.setDescription(dto.getDescription());
        event.setLocationId(locationId);
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setPublishedOn(Timestamp.valueOf(dto.getPublishedOn()));
        event.setRequestModeration(dto.isRequestModeration());
        event.setState(dto.getState());
        return event;
    }

    public FullEventDto toFullDto(Event event) {
        Location location = locationRepo.findById(event.getLocationId());
        Category category = categoryRepo.findById(event.getCategoryId());
        User user = userRepo.findById(event.getUserId());
        UserDto userDto = new UserDto(user.getId(), user.getName());
        return new FullEventDto(
                event.getAnnotation(),
                category,
                event.getConfirmedRequests(),
                event.getEventDate(),
                userDto,
                event.getPaid(),
                event.getTitle(),
                event.getViews(),
                event.getId(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getParticipantLimit(),
                location,
                event.getPublishedOn(),
                event.isRequestModeration(),
                event.getState()
        );
    }

    public EventCompDto toEventCompDto(FullEventDto data) {
        EventCompDto result = new EventCompDto();
        result.setAnnotation(data.getAnnotation());
        result.setCategory(data.getCategory());
        result.setConfirmedRequests(data.getConfirmedRequests());
        result.setEventDate(data.getEventDate());
        result.setId(data.getId());
        result.setInitiator(data.getInitiator());
        result.setPaid(data.getPaid());
        result.setTitle(data.getTitle());
        result.setViews(data.getViews());
        return result;
    }

    public EventCompDto toEventCompDto(Event event) {
        Category category = categoryRepo.findById(event.getCategoryId());
        EventCompDto result = new EventCompDto();
        User user = userRepo.findById(event.getUserId());
        UserDto userDto = new UserDto(user.getId(), user.getName());
        result.setAnnotation(event.getAnnotation());
        result.setCategory(category);
        result.setConfirmedRequests(event.getConfirmedRequests());
        result.setEventDate(String.valueOf(event.getEventDate()).replace("T", " "));
        result.setId(event.getId());
        result.setInitiator(userDto);
        result.setPaid(event.getPaid());
        result.setTitle(event.getTitle());
        result.setViews(event.getViews());
        return result;
    }
}
