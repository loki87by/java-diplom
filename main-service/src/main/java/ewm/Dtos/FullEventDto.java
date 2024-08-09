package ewm.Dtos;

import ewm.Objects.Category;
import ewm.Objects.Location;
import ewm.Objects.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class FullEventDto {
    private Long id;
    private String annotation;
    private int confirmedRequests;
    private Timestamp eventDate;
    private Boolean paid;
    private String title;
    private int views;
    private Category category;
    private User initiator;
    private Timestamp createdOn;
    private String description;
    private int participantLimit;
    private Location location;
    private Timestamp publishedOn;
    private boolean requestModeration;
    private String state;

    public FullEventDto(String annotation,
                        Category category,
                        int confirmedRequests,
                        Timestamp eventDate,
                        User initiator,
                        Boolean paid,
                        String title,
                        int views,
                        Long id,
                        Timestamp createdOn,
                        String description,
                        int participantLimit,
                        Location location,
                        Timestamp publishedOn,
                        boolean requestModeration,
                        String state) {
        this.annotation = annotation;
        this.confirmedRequests = confirmedRequests;
        this.eventDate = eventDate;
        this.paid = paid;
        this.title = title;
        this.views = views;
        this.category = category;
        this.id = id;
        this.createdOn = createdOn;
        this.description = description;
        this.participantLimit = participantLimit;
        this.location = location;
        this.initiator = initiator;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
    }
}
