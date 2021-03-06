package it.digiulio.social71.web.api.v1;

import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.NotFoundException;
import it.digiulio.social71.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionDescriptor<ValidationException>> serviceRequestExceptionResponse(ValidationException e) {
        ExceptionDescriptor<ValidationException> ret = new ExceptionDescriptor<>(e, HttpStatus.BAD_REQUEST);
        return ret.toResponseEntity();
    }

    @ExceptionHandler(BadServiceRequestException.class)
    public ResponseEntity<ExceptionDescriptor<BadServiceRequestException>> badServiceRequestExceptionResponse(BadServiceRequestException e) {
        ExceptionDescriptor<BadServiceRequestException> ret = new ExceptionDescriptor<>(e, HttpStatus.BAD_REQUEST);
        return ret.toResponseEntity();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDescriptor<NotFoundException>> notFoundExceptionResponse(NotFoundException e) {
        ExceptionDescriptor<NotFoundException> ret = new ExceptionDescriptor<>(e, HttpStatus.NOT_FOUND);
        return ret.toResponseEntity();
    }
}
