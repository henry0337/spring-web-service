package com.example.demo.services;

import com.example.demo.dto.JwtAuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.models.User;
import com.example.demo.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.example.demo.models.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    public User register(RegisterRequest request) {
        User user = new User();

        user.setName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(USER);

        return userRepository.save(user);
    }

    public JwtAuthResponse login(LoginRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password provided!"));
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwt);
        response.setRefreshToken(refreshToken);

        return response;
    }

    public JwtAuthResponse renewToken(RefreshTokenRequest request) {
        String email = jwtService.extractUsername(request.getToken());
        User user = userRepository.findByEmail(email).orElseThrow();

        if (jwtService.isTokenValid(request.getToken(), user)) {
            String jwt = jwtService.generateToken(user);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwt);
            response.setRefreshToken(request.getToken());

            return response;
        }
        return null;
    }
}
