package com.distibuido.chat_app.controllers;

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

    /
    @Autowired
    public ChatController(MessageService messageService, RoomService roomService, RabbitMQService rabbitMQService) {
        this.messageService = messageService;
        this.roomService = roomService;
        this.rabbitMQService = rabbitMQService;
    }


    @PostMapping("/rooms/{roomId}/messages")
    public ResponseEntity<Message> sendMessage(
            @PathVariable Long roomId, // ID da sala
            @RequestBody Message message // Corpo da mensagem enviada
    ) {

        if (!roomService.existsById(roomId)) {
            return ResponseEntity.notFound().build(); // Retorna 404 se a sala não for encontrada
        }


        if (!MessageValidator.isValidMessage(message)) {
            return ResponseEntity.badRequest().body(null); // Retorna 400 se a mensagem for inválida
        }


        message.setRoom(roomService.getRoomById(roomId));
        Message savedMessage = messageService.sendMessage(message);


        rabbitMQService.sendMessage(message.getContent());

        return ResponseEntity.status(201).body(savedMessage); // Retorna a mensagem salva com status 201 (Criado)
    }


    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessagesByRoom(
            @PathVariable Long roomId // ID da sala
    ) {

        if (!roomService.existsById(roomId)) {
            return ResponseEntity.notFound().build(); // Retorna 404 se a sala não for encontrada
        }


        List<Message> messages = messageService.getMessagesByRoomId(roomId);
        return ResponseEntity.ok(messages); // Retorna as mensagens com status 200 (OK)
    }
}
