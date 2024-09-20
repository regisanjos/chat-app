package com.distribuido.chat_app.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ManyToMany(mappedBy = "users")
    private Set<Room> rooms = new HashSet<>();  // Relacionamento com Room

    public User(String username) {
        this.username = username;
    }

    public User() {
    }


}
