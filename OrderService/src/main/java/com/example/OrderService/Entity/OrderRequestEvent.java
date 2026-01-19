package com.example.OrderService.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequestEvent {
    private Long userId;
    private Long coffeeId;
}
