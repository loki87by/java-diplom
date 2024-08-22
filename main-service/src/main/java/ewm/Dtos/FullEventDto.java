package ewm.Dtos;

import ewm.Entityes.Category;
import ewm.Entityes.Location;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
public class FullEventDto {
    private String annotation;
    private Category category;
    private int confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private Long id;
    private UserDto initiator;
    private LocationDto location;
    private Boolean paid;
    private int participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private String state;
    private String title;
    private int views;

    private String formatDateTime(Timestamp ts) {
        Timestamp current = Timestamp.from(Instant.now());

        if (ts != null) {
            current = ts;
        }
        String timestamp = current.toLocalDateTime().toString().replace("T", " ");
        int indexOfMilliSeconds = timestamp.indexOf(".");

        if (indexOfMilliSeconds < 0) {
            return timestamp;
        }
        return timestamp.substring(0, indexOfMilliSeconds);
    }

    public FullEventDto(String annotation,
                        Category category,
                        int confirmedRequests,
                        Timestamp eventDate,
                        UserDto initiator,
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
        this.eventDate = formatDateTime(eventDate);
        this.paid = paid;
        this.title = title;
        this.views = views;
        this.category = category;
        this.id = id;
        this.description = description;
        this.participantLimit = participantLimit;
        this.location = new LocationDto(location.getLat(), location.getLon());
        this.initiator = initiator;
        this.requestModeration = requestModeration;
        this.state = state;
        this.createdOn = formatDateTime(createdOn);
        this.publishedOn = formatDateTime(publishedOn);
    }
}
