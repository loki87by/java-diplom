package ewm.Errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    private final HttpStatus status;
    private final String reason;

    public ValidationException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.reason = "Validation Failed";
    }

}
