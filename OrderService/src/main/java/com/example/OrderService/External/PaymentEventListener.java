package com.example.OrderService.External;

import com.example.OrderService.Entity.Order;
import com.example.OrderService.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "PaymentEventTopic", groupId = "payment-group",containerFactory = "paymentListenerFactory")
    public void handlePaymentSuccess(PaymentSuccessEvent event){
        Order order = orderRepository.findByRazorpayOrderId(event.getRazorpayOrderId());

        if(order != null){
            order.setStatus("CONFIRMED");
            orderRepository.save(order);
            log.info("Order {} status updated to CONFIRMED", order.getId());
        }else {
            log.error("Order not found for Razorpay ID: {}", event.getRazorpayOrderId());
        }
    }
}
