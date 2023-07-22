package com.example.booking.controller;

import com.example.booking.dto.BookingDto;
import com.example.booking.dto.TransactionDto;
import com.example.booking.entities.Booking;
import com.example.booking.exceptions.InvalidTransactionIdException;
import com.example.booking.feign.PaymentServiceClient;
import com.example.booking.services.BookingService;
import com.example.booking.utils.BookingUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Period;

@RestController
@RequestMapping(value = "${bookingService.controller}")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentServiceClient paymentServiceClient;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(value = "${bookingService.bookRoomPath}",consumes = MediaType.APPLICATION_JSON,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto booking){
        Booking newBooking = Booking.builder()
                .fromDate(booking.getFromDate())
                .toDate(booking.getToDate())
                .aadharNumber(booking.getAadharNumber())
                .numOfRooms(booking.getNumOfRooms())
                .roomNumbers(BookingUtils.getRandomNumbers(booking.getNumOfRooms()).toString())
                .roomPrice(booking.getNumOfRooms() * 1000 * Period.between(booking.getFromDate().toLocalDate(),booking.getToDate().toLocalDate()).getDays())
                .transactionId(0)
                .bookedOn(LocalDateTime.now())
                .build();
        Booking savedMovie = bookingService.addBooking(newBooking);
        BookingDto savedBookingDto = modelMapper.map(savedMovie,BookingDto.class);
        return new ResponseEntity<>(savedBookingDto, HttpStatus.CREATED);
    }

    @PostMapping(value = "${bookingService.processBookingPath}",consumes = MediaType.APPLICATION_JSON,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<BookingDto> processBooking(@PathVariable(value = "bookingId") int bookingId, @RequestBody TransactionDto transaction){
        Booking existingBooking = bookingService.getBookingDetails(bookingId);
        //Send request to payment service to execute transaction using feign client
        TransactionDto transactionResponse = paymentServiceClient.addTransaction(transaction);
        System.out.println(transactionResponse.toString());
        if(transactionResponse.getTransactionId()==0){
            throw new InvalidTransactionIdException("Invalid transaction id");
        }
        Booking newBooking = Booking.builder()
                .fromDate(existingBooking.getFromDate())
                .toDate(existingBooking.getToDate())
                .aadharNumber(existingBooking.getAadharNumber())
                .numOfRooms(existingBooking.getNumOfRooms())
                .roomNumbers(BookingUtils.getRandomNumbers(existingBooking.getNumOfRooms()).toString())
                .roomPrice(existingBooking.getNumOfRooms() * 1000 * Period.between(existingBooking.getFromDate().toLocalDate(),existingBooking.getToDate().toLocalDate()).getDays())
                .transactionId(transactionResponse.getTransactionId())
                .build();
        Booking savedMovie = bookingService.updateBooking(transaction.getBookingId(),newBooking);
        BookingDto savedBookingDto = modelMapper.map(savedMovie,BookingDto.class);
        return new ResponseEntity<>(savedBookingDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "${bookingService.getBookingDetailsPath}",produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<BookingDto> getBookingDetails(@PathVariable("bookingId") int bookingId) {
        Booking booking = bookingService.getBookingDetails(bookingId);
        return ResponseEntity.ok(modelMapper.map(booking, BookingDto.class));
    }

}
