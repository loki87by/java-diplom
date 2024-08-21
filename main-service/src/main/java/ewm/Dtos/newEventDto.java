package ewm.Dtos;

import ewm.Entityes.Location;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class newEventDto {
    private String annotation;
    private Long categoryId;
    private String description;
    private Timestamp eventDate;
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;

    public newEventDto(String annotation,
                       Long categoryId,
                       String description,
                       Timestamp eventDate,
                       Location location,
                       Boolean paid,
                       int participantLimit,
                       boolean requestModeration,
                       String title) {
        this.annotation = annotation;
        this.eventDate = eventDate;
        this.paid = paid;
        this.title = title;
        this.participantLimit = participantLimit;
        this.categoryId = categoryId;
        this.location = location;
        this.description = description;
        this.requestModeration = requestModeration;
    }
}
