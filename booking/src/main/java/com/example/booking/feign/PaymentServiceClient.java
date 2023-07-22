package com.example.booking.feign;

import com.example.booking.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "API-GATEWAY")
public interface PaymentServiceClient {

    @RequestMapping(value = "${paymentService.controller}${paymentService.addTransactionPath}",method = RequestMethod.POST)
    public TransactionDto addTransaction(@RequestBody TransactionDto transactionDto);

    @RequestMapping(value = "${paymentService.controller}${paymentService.transactionDetailsPath}",method = RequestMethod.GET)
    public TransactionDto getTransactionDetails(@PathVariable("transactionId") int transactionId);
}
