package com.example.OrderService.Entity;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private Long coffeeId;
}
