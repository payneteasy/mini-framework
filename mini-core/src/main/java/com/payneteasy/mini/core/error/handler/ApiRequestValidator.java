package com.payneteasy.mini.core.error.handler;


import com.payneteasy.apiservlet.IRequestValidator;
import com.payneteasy.mini.core.error.exception.ApiBadRequestErrorException;
import com.payneteasy.mini.core.error.model.BadRequestError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class ApiRequestValidator implements IRequestValidator {

    private final Validator validator;

    public ApiRequestValidator() {
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Override
    public <T> T validateRequest(T aRequest, Class<T> aRequestClass) {
        if(aRequest == null) {
            throw new ApiBadRequestErrorException(BadRequestError.builder()
                    .errorCorrelationId(UUID.randomUUID().toString())
                    .errorCode(-7)
                    .errorMessage("No json body given")
                    .build());
        }

        Set<ConstraintViolation<T>>             validationResult = validator.validate(aRequest);
        ArrayList<BadRequestError.InvalidParam> invalidParams    = new ArrayList<>();
        if(validationResult.size() > 0) {
            for (ConstraintViolation<T> violation : validationResult) {
                invalidParams.add(BadRequestError.InvalidParam.builder()
                                .name(violation.getPropertyPath() + "")
                                .reason(violation.getMessage())
                        .build());
            }

            throw new ApiBadRequestErrorException(
                    BadRequestError.builder()
                            .errorCorrelationId(UUID.randomUUID().toString())
                            .errorCode(-1)
                            .errorMessage("Bad Parameters in request")
                            .invalidParams(invalidParams)
                            .build()
            );
        }
        return aRequest;
    }
}
