package com.distribuido.chat_app.controllers;

import com.distribuido.chat_app.models.Message;
import com.distribuido.chat_app.services.MessageService;
import com.distribuido.chat_app.services.RabbitMQService;
import com.distribuido.chat_app.services.RoomService;
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

    // Enviar mensagem para a sala
    @PostMapping("/rooms/{roomId}/messages")
    public ResponseEntity<Message> sendMessage(
            @PathVariable Long roomId,
            @RequestBody Message message) {

        // Verifica se a sala existe
        if (!roomService.existsById(roomId)) {
            return ResponseEntity.notFound().build(); // Retorna 404 se a sala não for encontrada
        }

        // Define a sala na mensagem
        message.setRoom(roomService.getRoomById(roomId).orElse(null));
        Message savedMessage = messageService.sendMessage(message); // Salva a mensagem
        rabbitMQService.sendMessage(message.getContent());  // Envia a mensagem ao RabbitMQ

        return ResponseEntity.status(201).body(savedMessage);  // Retorna a mensagem salva com status 201 (Created)
    }

    // Obter todas as mensagens da sala
    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessagesByRoom(
            @PathVariable Long roomId) {

        // Verifica se a sala existe
        if (!roomService.existsById(roomId)) {
            return ResponseEntity.notFound().build();  // Retorna 404 se a sala não for encontrada
        }

        // Obtém as mensagens da sala pelo ID
        List<Message> messages = messageService.getMessagesByRoomId(roomId);
        return ResponseEntity.ok(messages);  // Retorna as mensagens com status 200 (OK)
    }
}
