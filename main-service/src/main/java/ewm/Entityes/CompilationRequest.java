package ewm.Entityes;

import lombok.Data;

import java.util.List;

@Data
public class CompilationRequest {
    String title;
    Boolean pinned;
    List<Long> events;
}
