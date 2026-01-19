package com.example.UserService.Service;


import com.example.UserService.Entity.User;
import com.example.UserService.DTO.UserDTO;
import com.example.UserService.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(UserDTO request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        userRepository.save(user);
        return jwtService.generateToken(user);
    }


}

