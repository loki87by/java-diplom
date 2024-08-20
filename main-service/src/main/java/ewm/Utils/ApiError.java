package ewm.Utils;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Data
public class ApiError {
    List<String> errors;
    String message;
    String reason;
    HttpStatusCode status;
    String timestamp;

    public ApiError(List<StackTraceElement> list, String message, String reason, HttpStatusCode statusCode, String ts) {
    }
}
