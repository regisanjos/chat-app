package com.distribuido.chat_app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQService.class);
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendMessage(String message) {
        try {
            rabbitTemplate.convertAndSend("chat_exchange", "chat_routing_key", message);
            logger.info("Mensagem enviada para RabbitMQ: {}", message);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem para RabbitMQ", e);
        }
    }


    @RabbitListener(queues = "chat_queue")
    public void receiveMessage(String message) {
        try {

            logger.info("Mensagem recebida do RabbitMQ: {}", message);
        } catch (Exception e) {
            logger.error("Erro ao processar mensagem recebida do RabbitMQ", e);
        }
    }
}
