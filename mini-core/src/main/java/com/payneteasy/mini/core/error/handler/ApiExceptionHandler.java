package com.payneteasy.mini.core.error.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.payneteasy.apiservlet.IExceptionContext;
import com.payneteasy.apiservlet.IExceptionHandler;
import com.payneteasy.mini.core.error.exception.ApiErrorException;
import com.payneteasy.mini.core.error.model.BadRequestError;
import com.payneteasy.mini.core.error.model.IError;
import com.payneteasy.mini.core.error.model.InternalSystemError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


public class ApiExceptionHandler implements IExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handleException(Exception e, IExceptionContext aContext) {

        IError error;
        if (e instanceof ApiErrorException) {
            ApiErrorException errorException = (ApiErrorException) e;
            error = errorException.getError();

        } else if (e instanceof JsonSyntaxException) {
            error = BadRequestError.builder()
                    .errorMessage("Bad incoming json: " + e.getMessage())
                    .errorCorrelationId(UUID.randomUUID().toString())
                    .build();
        } else {
            error = InternalSystemError.builder()
                    .errorCode(-3)
                    .errorMessage("Unknown error")
                    .errorCorrelationId(UUID.randomUUID().toString())
                    .build();
        }
        HttpServletResponse response = aContext.getHttpResponse();
        response.setStatus(error.getHttpReasonCode());
        response.setHeader("Content-Type", "application/json; charset=utf-8");

        LOG.error("Error is {}", error, e);
        try {
            response.getWriter().write(gson.toJson(error));
        } catch (IOException ex) {
            LOG.error("Cannot write error", ex);
        }
    }
}
