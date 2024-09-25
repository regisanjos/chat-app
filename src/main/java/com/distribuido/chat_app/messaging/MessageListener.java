package com.distribuido.chat_app.messaging;

import com.distribuido.chat_app.websocket.ChatWebSocketHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    private final ChatWebSocketHandler chatWebSocketHandler;

    public MessageListener(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @RabbitListener(queues = "chat_queue")
    public void receiveMessage(String message) {
        try {

            String[] parts = message.split(",", 2); // Dividindo a mensagem para obter o conteúdo e a sala
            String content = parts[0]; // Conteúdo da mensagem
            String roomName = parts[1]; // Nome da sala


            chatWebSocketHandler.broadcastMessageToRoom(content, roomName);
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem: " + e.getMessage());
            e.printStackTrace(); // Debug apenas, pode ser substituído por um logger em produção
        }
    }
}
