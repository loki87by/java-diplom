package ewm.Errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    Instant now = Instant.now();
    String timestamp = Timestamp.from(now).toString();

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                Arrays.stream(ex.getStackTrace()).toList(),
                ex.getMessage(),
                ex.getReason(),
                ex.getStatus(),
                timestamp
        );
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, apiError, headers, ex.getStatus(), request);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                Arrays.stream(ex.getStackTrace()).toList(),
                ex.getMessage(),
                "SQL Error: " + ex.getSQLState(),
                HttpStatusCode.valueOf(403),
                timestamp
        );
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, apiError, headers, HttpStatusCode.valueOf(403), request);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                Arrays.stream(ex.getStackTrace()).toList(),
                ex.getMessage(),
                ex.getReason(),
                ex.getStatus(),
                timestamp
        );
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, apiError, headers, ex.getStatus(), request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                Arrays.stream(ex.getStackTrace()).toList(),
                ex.getMessage(),
                ex.getReason(),
                ex.getStatus(),
                timestamp
        );
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, apiError, headers, ex.getStatus(), request);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                Arrays.stream(ex.getStackTrace()).toList(),
                ex.getMessage(),
                ex.getReason(),
                ex.getStatus(),
                timestamp
        );
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, apiError, headers, ex.getStatus(), request);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                Arrays.stream(ex.getStackTrace()).toList(),
                ex.getMessage(),
                ex.getReason(),
                ex.getStatus(),
                timestamp
        );
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, apiError, headers, ex.getStatus(), request);
    }
}