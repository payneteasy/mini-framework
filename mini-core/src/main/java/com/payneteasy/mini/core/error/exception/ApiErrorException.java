package com.payneteasy.mini.core.error.exception;

import com.payneteasy.mini.core.error.model.IError;

public class ApiErrorException extends IllegalStateException {

    private final IError error;

    public ApiErrorException(IError aError, Throwable cause) {
        super(cause);
        error = aError;
    }

    public ApiErrorException(IError aError) {
        error = aError;
    }

    public IError getError() {
        return error;
    }
}
