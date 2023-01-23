package com.payneteasy.mini.core.error.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nullable;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(makeFinal = true, level = PRIVATE)
@Builder
public class BadRequestError implements IError {

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
            , example     = "No Authorization header"
    )
    String errorMessage;

    @Nullable
    @Schema(
            description = "List of bad input parameters"
    )
    List<InvalidParam> invalidParams;

    @Data
    @Builder
    @FieldDefaults(makeFinal = true, level = PRIVATE)
    public static class InvalidParam {
        @Schema(
                description = "Field name"
        )
        String name;
        @Schema(
                description = "Reason for error"
        )
        String reason;
    }

    @Override
    public int getHttpReasonCode() {
        return 400;
    }
}
