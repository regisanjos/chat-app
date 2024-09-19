package com.distribuido.chat_app.repository;

import com.distribuido.chat_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);


    boolean existsByUsername(String username);


    Optional<User> findByEmail(String email);
}
