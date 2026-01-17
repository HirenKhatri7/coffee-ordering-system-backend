package com.example.CoffeeService.Coffee;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository){
        this.coffeeRepository = coffeeRepository;
    }

    public Coffee saveCoffee(Coffee coffee){
        return coffeeRepository.save(coffee);
    }

    public Optional<Coffee> getCoffeeById(Long id){
        return coffeeRepository.findById(id);
    }

    public List<Coffee> getAllCoffee(){
        return (List<Coffee>)coffeeRepository.findAll();
    }

    public void deleteCoffeeById(Long id){
        coffeeRepository.deleteById(id);
    }

}
