package ewm.Mappers;

import ewm.Dtos.EventDto;
import ewm.Dtos.FullEventDto;
import ewm.Dtos.newEventDto;
import ewm.Entityes.*;
import ewm.Repositoryes.CategoryRepo;
import ewm.Repositoryes.EventsRepo;
import ewm.Repositoryes.LocationRepo;
import ewm.Repositoryes.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final LocationRepo locationRepo;
    private final EventsRepo eRepo;

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
        event.setCategoryId(dto.getCategoryId());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
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
        Event event = new Event();
        event.setId(dto.getId());
        event.setAnnotation(dto.getAnnotation());
        event.setCategoryId(dto.getCategory().getId());
        event.setConfirmedRequests(dto.getConfirmedRequests());
        event.setEventDate(dto.getEventDate());
        event.setUserId(userId);
        event.setPaid(dto.getPaid());
        event.setTitle(dto.getTitle());
        event.setViews(dto.getViews());
        event.setCompilationId(compilationId);
        event.setCreatedOn(dto.getCreatedOn());
        event.setDescription(dto.getDescription());
        event.setLocationId(dto.getLocation().getId());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setPublishedOn(dto.getPublishedOn());
        event.setRequestModeration(dto.isRequestModeration());
        event.setState(dto.getState());
        return event;
    }

    public FullEventDto toFullDto(Event event) {
        Location location = locationRepo.findById(event.getLocationId());
        Category category = categoryRepo.findById(event.getCategoryId());
        User user = userRepo.findById(event.getUserId());
        return new FullEventDto(
                event.getAnnotation(),
                category,
                event.getConfirmedRequests(),
                event.getEventDate(),
                user,
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
}
