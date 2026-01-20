package com.example.OrderService.External;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COFFEESERVICE", fallback = CoffeeClientFallback.class)
public interface CoffeeClient {
    @GetMapping("/coffee/{id}")
    CoffeeDTO getCoffeeById(@PathVariable Long id);
}
