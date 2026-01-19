package com.example.paymentService.DTO;

import lombok.Data;

@Data
public class PaymentVerificationRequest {
    private String orderId;
    private String paymentId;
    private String signature;
}
