package com.example.paymentService.controller;


import com.example.paymentService.DTO.PaymentVerificationRequest;
import com.example.paymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<String> initiatePayment(@RequestParam Long orderId, @RequestParam Double amount) {
        try {
            String razorpayOrderId = paymentService.initiatePayment(orderId, amount);
            return ResponseEntity.ok(razorpayOrderId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error initiating payment");
        }
    }


    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody PaymentVerificationRequest request) {
        boolean isValid = paymentService.verifyPayment(
                request.getOrderId(),
                request.getPaymentId(),
                request.getSignature()
        );

        if (isValid) {
            return ResponseEntity.ok("Payment Verified Successfully");
        } else {
            return ResponseEntity.status(400).body("Payment Verification Failed: Invalid Signature");
        }
    }
}
