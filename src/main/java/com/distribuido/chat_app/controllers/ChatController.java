package com.distribuido.chat_app.controllers;

import com.distribuido.chat_app.models.Message;
import com.distribuido.chat_app.services.MessageService;
import com.distribuido.chat_app.services.RabbitMQService;
import com.distribuido.chat_app.services.RoomService;
import com.distribuido.chat_app.utils.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final MessageService messageService;
    private final RoomService roomService;
    private final RabbitMQService rabbitMQService;


    @Autowired
    public ChatController(MessageService messageService, RoomService roomService, RabbitMQService rabbitMQService) {
        this.messageService = messageService;
        this.roomService = roomService;
        this.rabbitMQService = rabbitMQService;
    }


    @PostMapping("/rooms/{roomId}/messages")
    public ResponseEntity<Message> sendMessage(
            @PathVariable Long roomId,
            @RequestBody Message message
    ) {

        if (!roomService.existsById(roomId)) {
            return ResponseEntity.notFound().build();
        }


        if (!MessageValidator.isValidMessage(message)) {
            return ResponseEntity.badRequest().body(null);
        }


        message.setRoom(roomService.getRoomById(roomId));

        Message savedMessage = messageService.sendMessage(message);


        rabbitMQService.sendMessage(message.getContent());


        return ResponseEntity.status(201).body(savedMessage);
    }


    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessagesByRoom(
            @PathVariable Long roomId
    ) {

        if (!roomService.existsById(roomId)) {
            return ResponseEntity.notFound().build();
        }


        List<Message> messages = messageService.getMessagesByRoomId(roomId);

        return ResponseEntity.ok(messages);
    }
}
