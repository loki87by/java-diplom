package ewm.Entityes;

import lombok.Data;

import java.util.List;

@Data
public class RequestConfirmResponse {
    List<EventRequest> confirmedRequests = null;
    List<EventRequest> rejectedRequests = null;
}
