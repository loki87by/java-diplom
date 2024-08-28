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

}
