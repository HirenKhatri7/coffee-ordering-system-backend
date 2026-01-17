package com.example.OrderService;

import com.example.OrderService.External.CoffeeClient;
import com.example.OrderService.External.CoffeeDTO;
import com.example.OrderService.External.UserClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CoffeeClient  coffeeClient;
    private final UserClient userClient;

    public OrderService(OrderRepository orderRepository,
                        CoffeeClient coffeeClient,
                        UserClient userClient){
        this.orderRepository = orderRepository;
        this.coffeeClient = coffeeClient;
        this.userClient = userClient;
    }

    public Order save(Long userId, Long coffeeId){

        userClient.getUserById(userId);

        CoffeeDTO coffee = coffeeClient.getCoffeeById(coffeeId);

        Order o = new Order();
        o.setUserId(userId);
        o.setCoffeeId(coffeeId);
        o.setPrice(coffee.getPrice());
        o.setOrderTime(LocalDateTime.now());
        o.setStatus("CREATED");

        return orderRepository.save(o);
    }

    public List<Order> getAllOrder(){
        return (List<Order>) orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow();
    }

    public List<Order> getOrderByUserId(Long userId){
        return orderRepository.findByUserId(userId);
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }
}
