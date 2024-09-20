package com.distribuido.chat_app.services;

import com.distribuido.chat_app.models.Message;
import com.distribuido.chat_app.models.Room;
import com.distribuido.chat_app.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }


    public List<Message> getMessagesByRoomId(Long roomId) {
        Room room = new Room();
        room.setId(roomId);
        return messageRepository.findByRoomOrderByTimestampAsc(room);
    }
}
