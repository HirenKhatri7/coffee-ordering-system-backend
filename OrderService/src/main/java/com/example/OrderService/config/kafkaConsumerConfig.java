package com.example.OrderService.config;

import com.example.OrderService.Entity.OrderRequestEvent;
import com.example.OrderService.External.PaymentSuccessEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class kafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String,OrderRequestEvent> orderConsumeFactory(){
        JacksonJsonDeserializer<OrderRequestEvent> deserializer = new JacksonJsonDeserializer<>(OrderRequestEvent.class);

        deserializer.addTrustedPackages("com.example.OrderService");
        return new DefaultKafkaConsumerFactory<>(OrderConsumerConfig(),new StringDeserializer(),deserializer);
    }

    @Bean
    public Map OrderConsumerConfig(){
        Map props = new HashMap();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"order-group");
        return props;
    }
    @Bean
    public Map paymentConsumerConfig(){
        Map props = new HashMap();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"payment-group");
        return props;
    }

    @Bean
    public ConsumerFactory<String, PaymentSuccessEvent> paymentSuccessConsumeFactory(){

        JacksonJsonDeserializer<PaymentSuccessEvent> deserializer = new JacksonJsonDeserializer<>(PaymentSuccessEvent.class);

        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(paymentConsumerConfig(), new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentSuccessEvent> paymentListenerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, PaymentSuccessEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentSuccessConsumeFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderRequestEvent> orderRequestEventConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, OrderRequestEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderConsumeFactory());
        return factory;
    }
}
