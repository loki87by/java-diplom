package ewm.Dtos;

import ewm.Entityes.Location;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class newEventDto {
    private String annotation;
    private Long category;
    private String description;
    private Timestamp eventDate;
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;
    private Timestamp createdOn;

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
        this.eventDate = Timestamp.valueOf(eventDate);
        this.paid = paid;
        this.title = title;
        this.participantLimit = participantLimit;
        this.category = categoryId;
        this.location = location;
        this.description = description;
        this.requestModeration = requestModeration;
        this.createdOn = Timestamp.from(Instant.now());
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
