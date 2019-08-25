package com.soapboxrace.core.engine;

public class EngineException extends RuntimeException {
    private final EngineExceptionCode code;

    public EngineException(EngineExceptionCode code) {
        this.code = code;
    }

    public EngineException(String message, EngineExceptionCode code) {
        super(message);
        this.code = code;
    }

    public EngineException(String message, Throwable cause, EngineExceptionCode code) {
        super(message, cause);
        this.code = code;
    }

    public EngineException(Throwable cause, EngineExceptionCode code) {
        super(cause);
        this.code = code;
    }

    public EngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                           EngineExceptionCode code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public EngineExceptionCode getCode() {
        return code;
    }
}
