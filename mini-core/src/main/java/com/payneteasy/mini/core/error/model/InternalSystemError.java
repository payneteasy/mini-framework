package com.payneteasy.mini.core.error.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(makeFinal = true, level = PRIVATE)
@Builder
public class InternalSystemError implements IError {

    @Schema(
            description = "Unique number assigned by the system to identify error"
            , example     = "cc54bdd4-a57a-4683-8510-d1250218a813"
    )
    String errorCorrelationId;

    @Schema(
            description = "The error code"
            , example     = "1234"
    )
    int    errorCode;

    @Schema(
            description = "Description of the error"
            , example     = "Internal error"
    )
    String errorMessage;

    @Override
    public int getHttpReasonCode() {
        return 500;
    }
}
