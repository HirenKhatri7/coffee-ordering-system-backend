package com.example.OrderService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/")
    public Order placeOrder(@RequestBody OrderRequest orderRequest){
        return orderService.save(orderRequest.getUserId(),orderRequest.getCoffeeId());
    }

    @GetMapping("/")
    public List<Order> getAllOrders(){
        return orderService.getAllOrder();
    }

    @GetMapping("/{userId}")
    public List<Order> getOrderByUserId(@PathVariable Long userId){
        return orderService.getOrderByUserId(userId);
    }
}
