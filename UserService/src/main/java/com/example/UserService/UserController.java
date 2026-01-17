package com.example.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/")
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }

    @PostMapping("/")
    public User save(@RequestBody UserDTO user){
        return userService.save(user);
    }

    @GetMapping("/email/{emailId}")
    public User getUserByEmailId(@PathVariable String emailId){
        return userService.getUserByEmail(emailId);
    }


    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteById(id);
    }

}
