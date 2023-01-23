package com.payneteasy.mini.core.error.exception;

import com.payneteasy.mini.core.error.model.UnauthorizedError;

public class ApiUnauthorizedErrorException extends ApiErrorException{

    private final UnauthorizedError unauthorizedError;

    public ApiUnauthorizedErrorException(UnauthorizedError unauthorizedError, Throwable cause) {
        super(unauthorizedError, cause);
        this.unauthorizedError = unauthorizedError;
    }

    public ApiUnauthorizedErrorException(UnauthorizedError aError) {
        super(aError);
        this.unauthorizedError = aError;
    }

    public UnauthorizedError getUnauthorizedError() {
        return unauthorizedError;
    }
}
