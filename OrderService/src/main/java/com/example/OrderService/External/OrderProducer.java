package com.example.OrderService.External;

import com.example.OrderService.Entity.OrderRequestEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private final KafkaTemplate<String, OrderRequestEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderRequestEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderRequestEvent event){
        kafkaTemplate.send("order-requests",event);
    }
}
