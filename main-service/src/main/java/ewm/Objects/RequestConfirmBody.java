package ewm.Objects;

import lombok.Data;

import java.util.List;

@Data
public class RequestConfirmBody {
    List<Long> requestIds;
    String status = "PENDING";
}
