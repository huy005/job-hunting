package iiproject.jobhunting.exception;

import iiproject.jobhunting.dto.GenericResponse;
import iiproject.jobhunting.helpers.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserRestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<GenericResponse> handleException(UserNotFoundException exc) {
        GenericResponse error = new GenericResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setResponseContent(exc.getMessage());
        error.setTimeStamp(Utils.getTimeStampHelper());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<GenericResponse> handleException(Exception exc) {
        GenericResponse error = new GenericResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setResponseContent(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
