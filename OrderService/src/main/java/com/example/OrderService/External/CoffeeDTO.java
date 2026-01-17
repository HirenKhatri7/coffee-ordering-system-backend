package com.example.OrderService.External;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeDTO {
    private long id;
    private String name;
    private double price;
}
