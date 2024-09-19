package com.distribuido.chat_app.services;

import com.distribuido.chat_app.models.Message;
import com.distribuido.chat_app.models.Room;
import com.distribuido.chat_app.models.User;
import com.distribuido.chat_app.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public Message sendMessage(String content, User sender, Room room) {

        Objects.requireNonNull(content, "O conteúdo da mensagem não pode ser nulo.");
        Objects.requireNonNull(sender, "O remetente da mensagem não pode ser nulo.");
        Objects.requireNonNull(room, "A sala da mensagem não pode ser nula.");


        Message message = new Message(content, sender, room);
        message.setTimestamp(LocalDateTime.now()); // Define o timestamp da mensagem


        return messageRepository.save(message);
    }


    public List<Message> getMessagesByRoom(Room room) {

        Objects.requireNonNull(room, "A sala não pode ser nula.");

        return messageRepository.findByRoomOrderByTimestampAsc(room);
    }
}
