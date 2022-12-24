package com.netflix.sandBox.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiError {
    private String message;
    private String debugMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ApiError(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public ApiError(String message, String debugMessage, List<String> errors) {
        this.message = message;
        this.debugMessage = debugMessage;
        this.errors = errors;
    }

    public ApiError() {

    }
}