package com.netflix.sandBox.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

public class ApiError {
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String debugMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ApiError(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public ApiError(String message) {
        this.message = message;
    }

    public ApiError(String message, String debugMessage, List<String> errors) {
        this.message = message;
        this.debugMessage = debugMessage;
        this.errors = errors;
    }
}