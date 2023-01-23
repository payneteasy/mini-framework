package com.payneteasy.mini.core.error.exception;

import com.payneteasy.mini.core.error.model.InternalSystemError;

public class ApiInternalSystemErrorException extends ApiErrorException {

    private final InternalSystemError internalError;

    public ApiInternalSystemErrorException(Throwable cause, InternalSystemError internalError) {
        super(internalError, cause);
        this.internalError = internalError;
    }

    public InternalSystemError getInternalError() {
        return internalError;
    }
}
