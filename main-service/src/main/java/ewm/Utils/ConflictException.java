package ewm.Utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    private final HttpStatus status;
    private final String reason;

    public ConflictException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
        this.reason = "For the requested operation the conditions are not met.";
    }
}
