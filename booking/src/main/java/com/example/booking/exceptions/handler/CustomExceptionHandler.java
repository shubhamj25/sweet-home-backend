package com.example.booking.exceptions.handler;

import com.example.booking.exceptions.BookingNotFoundException;
import com.example.booking.exceptions.InvalidTransactionIdException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidTransactionIdException.class)
    public final ResponseEntity handleInvalidTransactionIdException(InvalidTransactionIdException e, WebRequest req){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("statusCode",HttpStatus.BAD_REQUEST.value());
        response.put("message",e.getLocalizedMessage());
        response.put("errorCode","INVALID_TRANSACTION_ID");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public final ResponseEntity handleBookingNotFoundException(BookingNotFoundException e, WebRequest req){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("statusCode",HttpStatus.BAD_REQUEST.value());
        response.put("message",e.getLocalizedMessage());
        response.put("errorCode","INVALID_BOOKING_ID");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.FeignClientException.class)
    public final ResponseEntity handleFeignClientExceptions(FeignException.FeignClientException e, WebRequest req){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("statusCode",HttpStatus.BAD_REQUEST.value());
        if(e.getLocalizedMessage().contains("errorCode") && e.getLocalizedMessage().contains("INVALID_PAYMENT_MODE")){
            response.put("message","Invalid mode of payment");
            response.put("errorCode","INVALID_PAYMENT_MODE");
        }
        else if(e.getLocalizedMessage().contains("errorCode") && e.getLocalizedMessage().contains("INVALID_BOOKING_ID")){
            response.put("message","Invalid Booking Id");
            response.put("errorCode","INVALID_BOOKING_ID");
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleGeneralException(Exception e, WebRequest req){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("statusCode",HttpStatus.BAD_REQUEST.value());
        response.put("message",e.getLocalizedMessage());
        response.put("errorCode","SOMETHING_WENT_WRONG");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
