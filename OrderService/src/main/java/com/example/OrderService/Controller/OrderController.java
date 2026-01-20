package com.example.OrderService.Controller;

import com.example.OrderService.Entity.Order;
import com.example.OrderService.Entity.OrderRequest;
import com.example.OrderService.Security.JwtValidationService;
import com.example.OrderService.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final JwtValidationService jwtValidationService;


    @PostMapping("/")
    public Order placeOrder(@RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String token){


        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            Long tokenUserId = jwtValidationService.extractUserId(jwtToken);
            if (tokenUserId == null || !tokenUserId.equals(orderRequest.getUserId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to place orders for this user ID");
            }
        }

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
