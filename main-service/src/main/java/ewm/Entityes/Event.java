package ewm.Entityes;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "annotation", nullable = false, length = 512)
    private String annotation;
    @Column(name = "category_id", nullable = false)
    private Long categoryId;
    @Column(name = "confirmed_requests")
    private int confirmedRequests = 0;
    @Column(name = "event_date")
    private Timestamp eventDate;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "paid")
    private Boolean paid;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "views")
    private int views = 0;
    @Column(name = "compilation_id")
    private Long compilationId;
    @Column(name = "created_on", insertable = false, updatable = false)
    private Timestamp createdOn;
    @Column(name = "description", nullable = false, length = 512)
    private String description;
    @Column(name = "location_id", nullable = false)
    private Long locationId;
    @Column(name = "participant_limit")
    private int participantLimit;
    @Column(name = "published_on")
    private Timestamp publishedOn;
    @Column(name = "request_moderation")
    private boolean requestModeration;
    @Column(name = "state")
    private String state = "PENDING";

    public Event(Event event) {
        this.id = event.getId();
        this.annotation = event.getAnnotation();
        this.categoryId = event.getCategoryId();
        this.confirmedRequests = event.getConfirmedRequests();
        this.eventDate = event.getEventDate();
        this.userId = event.getUserId();
        this.paid = event.getPaid();
        this.title = event.getTitle();
        this.views = event.getViews();
        this.compilationId = event.getCompilationId();
        this.createdOn = event.getCreatedOn();
        this.description = event.getDescription();
        this.locationId = event.getLocationId();
        this.participantLimit = event.getParticipantLimit();
        this.publishedOn = event.getPublishedOn();
        this.requestModeration = event.isRequestModeration();
        this.state = event.getState();
    }

    public Event() {}
}
