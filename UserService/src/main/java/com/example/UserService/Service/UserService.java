package com.example.UserService.Service;

import com.example.UserService.Entity.User;
import com.example.UserService.DTO.UserDTO;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public List<User> getAllUser(){
        return (List<User>) userRepository.findAll();
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public User save(UserDTO user){
        User u = new User();
        u.setEmail(user.getEmail());
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setCreatedAt(new Date(System.currentTimeMillis()));
        return userRepository.save(u);
    }


    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User not found with email: " + username));
    }
}
