package com.distribuido.chat_app.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;


    @Column(nullable = false)
    private LocalDateTime timestamp;


    public Message() {}


    public Message(String content, User sender, Room room) {
        this.content = content;
        this.sender = sender;
        this.room = room;
        this.timestamp = LocalDateTime.now();
    }


}
