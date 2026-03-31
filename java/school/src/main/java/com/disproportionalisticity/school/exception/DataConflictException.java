package com.disproportionalisticity.school.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataConflictException extends RuntimeException {

    public DataConflictException(String message) {
        super(message);
    }

    public DataConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}