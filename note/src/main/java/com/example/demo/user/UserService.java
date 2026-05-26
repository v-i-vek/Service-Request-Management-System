package com.example.demo.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service()
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> user = this.userRepository.findAll();
        return user;
    }

    public UserDto getUserById(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        return this.userRepository.save(userDto);

    }

    public UserDto updateUser(UserDto userDto, String id) {
        UserDto exisitingUser = this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        exisitingUser.setName(userDto.getName());
        exisitingUser.setEmail(userDto.getEmail());

        return this.userRepository.save(exisitingUser);
    }

    public int deleteUser(String id) {

        try {
            this.userRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            throw new RuntimeException("User not found with id: " + id);

        }

    }
}
