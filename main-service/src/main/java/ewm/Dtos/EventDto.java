package ewm.Dtos;

import ewm.Entityes.Category;
import ewm.Entityes.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class EventDto {
    private String annotation;
    private int confirmedRequests;
    private Timestamp eventDate;
    private Boolean paid;
    private String title;
    private int views;
    private Category category;
    private User initiator;

    public EventDto(String annotation,
                    Category category,
                    int confirmedRequests,
                    Timestamp eventDate,
                    User initiator,
                    Boolean paid,
                    String title,
                    int views) {
        this.annotation = annotation;
        this.confirmedRequests = confirmedRequests;
        this.eventDate = eventDate;
        this.paid = paid;
        this.title = title;
        this.views = views;
        this.category = category;
        this.initiator = initiator;
    }
}
