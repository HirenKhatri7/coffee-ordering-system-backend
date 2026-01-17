package com.example.OrderService;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private Long coffeeId;
}
