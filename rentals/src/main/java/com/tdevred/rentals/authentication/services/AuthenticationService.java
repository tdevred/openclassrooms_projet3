package com.tdevred.rentals.authentication.services;

import com.tdevred.rentals.authentication.dtos.LoginUserDto;
import com.tdevred.rentals.authentication.dtos.RegisterUserDto;
import com.tdevred.rentals.authentication.entities.User;
import com.tdevred.rentals.authentication.exceptions.NonUniqueUserIdentifierException;
import com.tdevred.rentals.authentication.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) throws NonUniqueUserIdentifierException {
        User user = new User();

        // S'assurer que l'email et le nom soient uniques
        if(userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new NonUniqueUserIdentifierException("User email already used.");
        }

        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public Optional<User> authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail());
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}