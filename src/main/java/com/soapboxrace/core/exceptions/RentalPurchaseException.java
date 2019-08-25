package com.soapboxrace.core.exceptions;

public class RentalPurchaseException extends RuntimeException {
    public RentalPurchaseException() {
    }

    public RentalPurchaseException(String message) {
        super(message);
    }

    public RentalPurchaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentalPurchaseException(Throwable cause) {
        super(cause);
    }

    public RentalPurchaseException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
