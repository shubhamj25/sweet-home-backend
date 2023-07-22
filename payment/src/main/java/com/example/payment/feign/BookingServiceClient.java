package com.example.payment.feign;

import com.example.payment.dto.BookingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name = "API-GATEWAY")
public interface BookingServiceClient {

    @RequestMapping(value = "${bookingService.controller}${bookingService.getBookingDetailsPath}",method = RequestMethod.GET)
    public BookingDto getBookingDetails(@PathVariable("bookingId") int bookingId);
}
