package com.example.OrderService.External;

import com.example.OrderService.Security.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERSERVICE",configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
