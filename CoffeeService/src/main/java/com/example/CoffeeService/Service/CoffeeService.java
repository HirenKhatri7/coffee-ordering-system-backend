package com.example.CoffeeService.Service;

import com.example.CoffeeService.DTO.CoffeeDTO;
import com.example.CoffeeService.Entity.Coffee;
import com.example.CoffeeService.Repository.CoffeeRepository;
import com.example.CoffeeService.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository){
        this.coffeeRepository = coffeeRepository;
    }

    public Coffee saveCoffee(CoffeeDTO coffee){
        Coffee c = new Coffee();
        c.setName(coffee.getName());
        c.setPrice(coffee.getPrice());
        return coffeeRepository.save(c);
    }

    public Coffee getCoffeeById(Long id){
        return coffeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Coffee not found with id: " + id));
    }

    public List<Coffee> getAllCoffee(){
        return (List<Coffee>)coffeeRepository.findAll();
    }

    public void deleteCoffeeById(Long id){
        coffeeRepository.deleteById(id);
    }

}
