package ewm.Dtos;

import ewm.Entityes.Location;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class EventUpdateResponse {
    private final String stateAction;
    private final Timestamp eventDate;
    private final String annotation;
    private final Long categoryId;
    private final String description;
    private final Location location;
    private final Boolean paid;
    private final int participantLimit;
    private final boolean requestModeration;
    private final String title;

    public EventUpdateResponse(
        String annotation,
        int category,
        String description,
        String eventDate,
        Location location,
        Boolean paid,
        int participantLimit,
        boolean requestModeration,
        String stateAction,
        String title) {
        this.categoryId = Long.parseLong(String.valueOf(category));
        this.location = location;
        this.participantLimit = participantLimit;
        this.title = title;
        this.requestModeration = requestModeration;
        this.paid = paid;
        this.description = description;
        this.annotation = annotation;
        this.stateAction = stateAction;
        this.eventDate = Timestamp.valueOf(eventDate);
    }
}
