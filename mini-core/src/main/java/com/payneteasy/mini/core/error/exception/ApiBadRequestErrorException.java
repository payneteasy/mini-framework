package com.payneteasy.mini.core.error.exception;

import com.payneteasy.mini.core.error.model.BadRequestError;

public class ApiBadRequestErrorException extends ApiErrorException {

    private final BadRequestError badRequestError;

    public ApiBadRequestErrorException(int aCode, String aMessage) {
        this(BadRequestError.builder()
                .errorCode(aCode)
                .errorMessage(aMessage)
                .build());
    }

    public ApiBadRequestErrorException(BadRequestError badParameterError, Throwable cause) {
        super(badParameterError, cause);
        this.badRequestError = badParameterError;
    }

    public ApiBadRequestErrorException(BadRequestError badParameterError) {
        super(badParameterError);
        this.badRequestError = badParameterError;
    }

    public BadRequestError getBadRequestError() {
        return badRequestError;
    }
}
