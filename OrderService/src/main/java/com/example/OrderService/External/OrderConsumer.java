    package com.example.OrderService.External;

    import com.example.OrderService.Entity.Order;
    import com.example.OrderService.Repository.OrderRepository;
    import com.example.OrderService.Entity.OrderRequestEvent;
    import org.springframework.kafka.annotation.KafkaListener;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;

    @Service
    public class OrderConsumer {
        private final OrderRepository orderRepository;
        private final CoffeeClient  coffeeClient;
        private final UserClient userClient;

        public OrderConsumer(OrderRepository orderRepository,
                             CoffeeClient coffeeClient,
                             UserClient userClient){
            this.orderRepository = orderRepository;
            this.coffeeClient = coffeeClient;
            this.userClient = userClient;
        }

        @KafkaListener(topics = "order-requests", groupId = "order-group", containerFactory = "orderRequestEventConcurrentKafkaListenerContainerFactory")
        public void consume(OrderRequestEvent event){
            UserDTO user = userClient.getUserById(event.getUserId());
            System.out.println(user.toString());
            CoffeeDTO coffee = coffeeClient.getCoffeeById(event.getCoffeeId());

            Order o = new Order();
            o.setUserId(event.getUserId());
            o.setCoffeeId(event.getCoffeeId());
            o.setPrice(coffee.getPrice());
            o.setOrderTime(LocalDateTime.now());
            o.setStatus("CREATED");

            orderRepository.save(o);
        }
    }
