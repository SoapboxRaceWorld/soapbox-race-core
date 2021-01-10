/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.engine;

import com.soapboxrace.core.bo.ErrorReportingBO;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ejb.EJB;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EngineExceptionHandler implements ExceptionMapper<Exception> {

    @EJB
    private ErrorReportingBO errorReportingBO;

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof EngineException) {
            EngineException engineException = (EngineException) exception;
            EngineExceptionTrans engineExceptionTrans = new EngineExceptionTrans();
            String stackTrace = ExceptionUtils.getStackTrace(exception);

            if (engineException.isFatal()) {
                errorReportingBO.sendException(exception);
            }

            engineExceptionTrans.setErrorCode(engineException.getCode().getErrorCode());
            engineExceptionTrans.setStackTrace(stackTrace);
            engineExceptionTrans.setInnerException(new EngineInnerExceptionTrans());
            engineExceptionTrans.getInnerException().setErrorCode(engineExceptionTrans.getErrorCode());
            engineExceptionTrans.getInnerException().setStackTrace(stackTrace);

            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .type(MediaType.APPLICATION_XML_TYPE)
                    .entity(engineExceptionTrans).build();
        } else {
            if (exception instanceof NotAuthorizedException) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            errorReportingBO.sendException(exception);
            EngineExceptionTrans engineExceptionTrans = new EngineExceptionTrans();
            String stackTrace = ExceptionUtils.getStackTrace(exception);

            engineExceptionTrans.setErrorCode(EngineExceptionCode.UnspecifiedError.getErrorCode());
            engineExceptionTrans.setStackTrace(stackTrace);
            engineExceptionTrans.setInnerException(new EngineInnerExceptionTrans());
            engineExceptionTrans.getInnerException().setErrorCode(engineExceptionTrans.getErrorCode());
            engineExceptionTrans.getInnerException().setStackTrace(stackTrace);

            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .type(MediaType.APPLICATION_XML_TYPE)
                    .entity(engineExceptionTrans).build();
        }
    }
}
