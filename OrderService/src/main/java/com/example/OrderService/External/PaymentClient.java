package com.example.OrderService.External;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PAYMENTSERVICE")
public interface PaymentClient {
    @PostMapping("/payments/initiate")
    ResponseEntity<String> initiatePayment(@RequestParam Long orderId, @RequestParam Double amount);
}