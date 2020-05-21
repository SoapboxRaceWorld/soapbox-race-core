/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.engine;

import javax.ejb.ApplicationException;

@ApplicationException
public class EngineException extends RuntimeException {
    private final EngineExceptionCode code;

    private final boolean fatal;

    public EngineException(EngineExceptionCode code, boolean fatal) {
        this.code = code;
        this.fatal = fatal;
    }

    public EngineException(String message, EngineExceptionCode code, boolean fatal) {
        super(message);
        this.code = code;
        this.fatal = fatal;
    }

    public EngineException(String message, Throwable cause, EngineExceptionCode code, boolean fatal) {
        super(message, cause);
        this.code = code;
        this.fatal = fatal;
    }

    public EngineException(Throwable cause, EngineExceptionCode code, boolean fatal) {
        super(cause);
        this.code = code;
        this.fatal = fatal;
    }

    public EngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                           EngineExceptionCode code, boolean fatal) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.fatal = fatal;
    }

    public EngineExceptionCode getCode() {
        return code;
    }

    public boolean isFatal() {
        return fatal;
    }
}
