package ewm.Dtos;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;

    public UserDto(Long id, String name) {
        this.name = name;
        this.id = id;
    }
}
