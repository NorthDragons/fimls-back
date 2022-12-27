package com.netflix.sandBox.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class MovieExceptionHandler {
    private final Logger log = Logger.getLogger(MovieExceptionHandler.class);
    private ApiError apiError;

    @ExceptionHandler(MovieNotFoundException.class)
    protected ResponseEntity<Object> movieNotFoundException(MovieNotFoundException ex,
                                                            WebRequest request) {
        apiError = new ApiError("Movie not found exception", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Internal error: ", ex);
        apiError = new ApiError(
                "Sorry, an unknown server error has occurred, contact your system administrator" +
                        "for more details");
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
