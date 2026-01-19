package com.example.CoffeeService.Repository;

import com.example.CoffeeService.Entity.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
