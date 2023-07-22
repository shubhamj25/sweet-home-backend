package com.example.payment.exceptions;

public class InvalidPaymentModeException extends RuntimeException {
    public InvalidPaymentModeException(String message) {
        super(message);
    }
}
