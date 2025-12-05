package com.onboardingassignment.oa.services;

import com.onboardingassignment.oa.model.User;
import com.onboardingassignment.oa.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String password, String email, String role) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setRole(role);

        userRepository.save(newUser);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
