package com.example.payment.controller;

import com.example.payment.dto.BookingDto;
import com.example.payment.dto.TransactionDto;
import com.example.payment.entities.Transaction;
import com.example.payment.feign.BookingServiceClient;
import com.example.payment.services.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "${paymentService.controller}")
public class TransactionController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingServiceClient bookingServiceClient;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(value = "${paymentService.addTransactionPath}")
    public ResponseEntity<TransactionDto> addTransaction(@RequestBody TransactionDto transactionDto) {
        BookingDto fetchedBooking = bookingServiceClient.getBookingDetails(transactionDto.getBookingId());
        Transaction transaction = Transaction.builder()
                .paymentMode(transactionDto.getPaymentMode())
                .bookingId(transactionDto.getBookingId())
                .upiId(transactionDto.getUpiId())
                .cardNumber(transactionDto.getCardNumber())
                .build();
        Transaction savedTransaction = paymentService.processTransaction(transaction);
        TransactionDto savedTransactionDto = modelMapper.map(savedTransaction, TransactionDto.class);
        return new ResponseEntity<>(savedTransactionDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "${paymentService.transactionDetailsPath}")
    public ResponseEntity<TransactionDto> getTransactionDetails(@PathVariable(value = "transactionId") int transactionId){
        return ResponseEntity.ok(modelMapper.map(paymentService.getTransactionDetails(transactionId), TransactionDto.class));
    }
}
