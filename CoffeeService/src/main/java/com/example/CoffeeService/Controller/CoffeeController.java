package com.example.CoffeeService.Controller;
import com.example.CoffeeService.DTO.CoffeeDTO;
import com.example.CoffeeService.Entity.Coffee;
import com.example.CoffeeService.Service.CoffeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    private final CoffeeService coffeeService;
    public CoffeeController(CoffeeService coffeeService){
        this.coffeeService = coffeeService;
    }

    @GetMapping("/")
    public List<Coffee> getAllCoffee(){
        return coffeeService.getAllCoffee();
    }

    @GetMapping("/{id}")
    public Coffee getById(@PathVariable Long id){
        return coffeeService.getCoffeeById(id);
    }

    @PostMapping("/")
    public Coffee addCoffee(@RequestBody CoffeeDTO coffee){
        return coffeeService.saveCoffee(coffee);
    }

    @DeleteMapping("/{id}")
    public void deleteCoffee(@PathVariable Long id){
        coffeeService.deleteCoffeeById(id);
    }
}
