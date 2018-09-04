package com.nostratech.project.exception;

import com.nostratech.project.enums.StatusCode;
import lombok.Data;
import lombok.Getter;

/**
 * Created by yukibuwana on 1/24/17.
 */

public class NostraException extends RuntimeException {

    @Getter
    private StatusCode code = StatusCode.ERROR;

    public NostraException() {
        super();
    }

    public NostraException(String message) {
        super(message);
    }

    public NostraException(String message, StatusCode code) {
        super(message);
        this.code = code;
    }
}
