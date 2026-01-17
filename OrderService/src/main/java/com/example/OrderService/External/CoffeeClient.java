package com.example.OrderService.External;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COFFEESERVICE")
public interface CoffeeClient {
    @GetMapping("/{id}")
    CoffeeDTO getCoffeeById(@PathVariable Long id);
}
