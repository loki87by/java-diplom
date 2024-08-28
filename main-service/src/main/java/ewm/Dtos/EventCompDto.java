package ewm.Dtos;

import ewm.Entityes.Category;

import lombok.Data;

@Data
public class EventCompDto {
    private String annotation;
    private Category category;
    private int confirmedRequests;
    private String eventDate;
    private Long id;
    private UserDto initiator;
    private Boolean paid;
    private String title;
    private int views;

}
