package com.loyaltyplant.test.service;

/**
 * System created Order that can not be processed.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public class OrderProcessingException extends IllegalStateException {

    public OrderProcessingException() {
    }

    public OrderProcessingException(String s) {
        super(s);
    }

    public OrderProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderProcessingException(Throwable cause) {
        super(cause);
    }
}
