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

            chatWebSocketHandler.broadcastMessage(message);
        } catch (Exception e) {

            System.err.println("Erro ao processar mensagem: " + e.getMessage());
            e.printStackTrace(); // Debug apenas, mas substituível por um logger em produção
        }
    }
}
