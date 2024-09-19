package com.distribuido.chat_app.services;

import com.distribuido.chat_app.models.User;
import com.distribuido.chat_app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(String username) {

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }


        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username is already in use");
        }


        User user = new User(username, generateDefaultPassword());


        User savedUser = userRepository.save(user);
        logger.info("Usuário criado: {}", username);

        return savedUser;
    }


    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }


    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    private String generateDefaultPassword() {
        return "defaultPassword123"; // Substituir por uma senha gerada dinamicamente
    }
}
