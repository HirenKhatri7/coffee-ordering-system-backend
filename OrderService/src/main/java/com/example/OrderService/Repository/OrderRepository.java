package com.example.OrderService.Repository;

import com.example.OrderService.Entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByUserId(Long id);
    Order findByRazorpayOrderId(String razorpayOrderId);
}
