package ewm.Errors;

import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    private final HttpStatus status;
    private final String reason;
    private final String message;
    private final Timestamp ts;

    public ConflictException(String message) {
        this.status = HttpStatus.CONFLICT;
        this.message = message;
        this.ts = Timestamp.from(Instant.now());
        this.reason = "For the requested operation the conditions are not met.";
    }
}
