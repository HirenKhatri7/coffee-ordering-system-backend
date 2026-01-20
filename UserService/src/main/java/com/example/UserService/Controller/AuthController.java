package com.example.UserService.Controller;

import com.example.UserService.DTO.AuthRequest;
import com.example.UserService.Entity.User;
import com.example.UserService.Service.AuthService;
import com.example.UserService.Service.JwtService;
import com.example.UserService.Service.UserService;
import com.example.UserService.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        // If we get here, auth was successful
        final User user = userService.getUserByEmail(request.getUsername());
        Map<String, Object> claims = Map.of("userId",user.getId());
        return ResponseEntity.ok(jwtService.generateToken(user,claims));
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
