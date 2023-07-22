package com.example.booking.exceptions;

public class InvalidTransactionIdException extends RuntimeException{
    public InvalidTransactionIdException(String message) {
        super(message);
    }
}
