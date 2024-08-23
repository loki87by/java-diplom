package ewm.Dtos;

import ewm.Entityes.Location;
import lombok.Data;

@Data
public class newEventDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;
    private String createdOn;

/*    public newEventDto(String annotation,
                       Long categoryId,
                       String description,
                       String eventDate,
                       Location location,
                       Boolean paid,
                       int participantLimit,
                       boolean requestModeration,
                       String title) {
        this.annotation = annotation;
        this.eventDate = String.valueOf(eventDate);
        this.paid = paid;
        this.title = title;
        this.participantLimit = participantLimit;
        this.category = categoryId;
        this.location = location;
        this.description = description;
        this.requestModeration = requestModeration;
        this.createdOn = String.from(Instant.now());
    }*/
/*
    public newEventDto(String annotation,
                       Long categoryId,
                       String description,
                       Location location,
                       Boolean paid,
                       int participantLimit,
                       boolean requestModeration,
                       String title) {
        this.annotation = annotation;
        this.paid = paid;
        this.title = title;
        this.participantLimit = participantLimit;
        this.category = categoryId;
        this.location = location;
        this.description = description;
        this.requestModeration = requestModeration;
    }*/

}
