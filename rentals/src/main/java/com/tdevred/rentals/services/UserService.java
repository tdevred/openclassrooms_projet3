package com.tdevred.rentals.services;

import com.tdevred.rentals.authentication.entities.User;
import com.tdevred.rentals.authentication.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }
}
