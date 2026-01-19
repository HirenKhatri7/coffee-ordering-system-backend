package com.example.paymentService.service;

import com.example.paymentService.DTO.PaymentSuccessEvent;
import com.example.paymentService.entity.Payment;
import com.example.paymentService.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, PaymentSuccessEvent> kafkaTemplate;

    @Value("${razorpay.key}")
    private String apiKey;
    @Value("${razorpay.secret}")
    private String apiSecret;

    public String initiatePayment(Long orderId, Double amount) throws Exception{
        RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount*100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt","Order_rcptid_" + orderId);

        Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        Payment payment = Payment.builder().orderId(orderId).razorpayOrderId(razorpayOrder.get("id")).status("PENDING").amount(amount).build();

        paymentRepository.save(payment);
        return payment.getRazorpayOrderId();
    }

    public boolean verifyPayment(String orderId, String paymentId, String signature) {
        try {
            // 1. Create a signature string using the order_id and payment_id
            String payload = orderId + "|" + paymentId;

            // 2. Use Razorpay's built-in verify method
            // It throws an exception if the signature doesn't match
            Utils.verifyPaymentSignature(new JSONObject()
                    .put("razorpay_order_id", orderId)
                    .put("razorpay_payment_id", paymentId)
                    .put("razorpay_signature", signature), apiSecret);

            // 3. If we reach here, verification was successful
            Payment payment = paymentRepository.findByRazorpayOrderId(orderId).orElseThrow();
            payment.setRazorpayPaymentId(paymentId);
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);

            PaymentSuccessEvent event = new PaymentSuccessEvent(orderId, paymentId);
            kafkaTemplate.send("PaymentEventTopic",event);
            return true;

        } catch (Exception e) {
            // Signature mismatch or other error
            e.printStackTrace();
            return false;
        }
    }
}
