package com.distribuido.chat_app.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(MessagePublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;


    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = "chat_exchange";
    }


    public void sendMessage(String message) {
        try {
            String routingKey = "chat_routing_key";
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            logger.info("Mensagem enviada para RabbitMQ: {}", message);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem para RabbitMQ: {}", e.getMessage(), e);
        }
    }
}
