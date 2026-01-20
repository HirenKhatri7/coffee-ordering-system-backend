package com.example.OrderService.External;

import org.springframework.stereotype.Component;

@Component
public class CoffeeClientFallback implements CoffeeClient{
    @Override
    public CoffeeDTO getCoffeeById(Long id) {
        CoffeeDTO defaultCoffee = new CoffeeDTO();
        defaultCoffee.setId(id);
        defaultCoffee.setName("Service Unavailable");
        defaultCoffee.setPrice(0.0);
        return defaultCoffee;
    }
}
