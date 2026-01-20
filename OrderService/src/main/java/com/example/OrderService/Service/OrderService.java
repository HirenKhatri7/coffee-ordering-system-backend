package com.example.OrderService.Service;

import com.example.OrderService.External.CoffeeClient;
import com.example.OrderService.External.CoffeeDTO;
import com.example.OrderService.Entity.Order;
import com.example.OrderService.External.PaymentClient;
import com.example.OrderService.Repository.OrderRepository;
import com.example.OrderService.Entity.OrderRequestEvent;
import com.example.OrderService.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CoffeeClient coffeeClient;
    private final PaymentClient paymentClient;

    public Order save(Long userId, Long coffeeId){
        CoffeeDTO coffee = coffeeClient.getCoffeeById(coffeeId);

        Order o = new Order();
        o.setUserId(userId);
        o.setCoffeeId(coffeeId);
        o.setPrice(coffee.getPrice());
        o.setOrderTime(LocalDateTime.now());
        o.setStatus("PENDING_PAYMENT");
        ResponseEntity<String> paymentResponse = paymentClient.initiatePayment(o.getId(), o.getPrice());
        String razorpayOrderId = paymentResponse.getBody();
        o.setRazorpayOrderId(razorpayOrderId);
        return orderRepository.save(o);
    }

    public List<Order> getAllOrder(){
        return (List<Order>) orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
    }

    public List<Order> getOrderByUserId(Long userId){
        return orderRepository.findByUserId(userId);
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }
}
