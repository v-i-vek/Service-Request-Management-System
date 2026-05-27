package com.example.demo.auth;

import org.apache.catalina.User;
import org.springframework.boot.security.oauth2.server.resource.autoconfigure.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.security.JwtUtil;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserRepository;

@Service()
public class AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String login(AuthDto authDto) {
        UserDto user = this.userRepository.findByEmail(authDto.email())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + authDto.email()));
        if (!this.passwordEncoder.matches(authDto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password or email");
        }
        String JwtToken = this.jwtUtil.generateToken(user.getEmail());
        return JwtToken;
    }

}
