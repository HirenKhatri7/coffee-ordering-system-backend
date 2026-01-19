package com.example.paymentService.config;


import com.example.paymentService.DTO.PaymentSuccessEvent;
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
    public ConsumerFactory<String, PaymentSuccessEvent> paymentSuccessEventConsumerFactory(){
        JacksonJsonDeserializer<PaymentSuccessEvent> deserializer = new JacksonJsonDeserializer<>(PaymentSuccessEvent.class);

        deserializer.addTrustedPackages("com.example.paymentService");
        return new DefaultKafkaConsumerFactory<>(consumerConfig(),new StringDeserializer(),deserializer);
    }

        @Bean
        public Map consumerConfig(){
            Map props = new HashMap();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ConsumerConfig.GROUP_ID_CONFIG,"payment-group");
            return props;
        }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentSuccessEvent> paymentSuccessEventConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, PaymentSuccessEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentSuccessEventConsumerFactory());
        return factory;
    }
}
