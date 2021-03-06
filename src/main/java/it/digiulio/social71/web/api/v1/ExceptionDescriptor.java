package it.digiulio.social71.web.api.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.digiulio.social71.exception.CustomException;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;

@Getter
public class ExceptionDescriptor<T extends CustomException> {
    private final HttpStatus status;

    @JsonIgnore
    private final T exception;

    private final String type;

    private final ZonedDateTime timestamp;

    private final String message;

    public ExceptionDescriptor(
            @NonNull T exception,
            @NonNull HttpStatus status,
            @NonNull ZonedDateTime timestamp
    ) {
        this.exception = exception;
        this.type = exception.getClass().getSimpleName();
        this.status = status;
        this.timestamp = timestamp;
        this.message=exception.getMessage();
    }

    public ExceptionDescriptor(
            @NonNull T exception,
            @NonNull HttpStatus status
    ) {
        this(exception, status, ZonedDateTime.now());
    }

    public ResponseEntity<ExceptionDescriptor<T>> toResponseEntity() {
        return new ResponseEntity<>(this, this.status);
    }
}
