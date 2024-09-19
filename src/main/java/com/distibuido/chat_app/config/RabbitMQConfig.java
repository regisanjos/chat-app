package com.distibuido.chat_app.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    public static final String CHAT_QUEUE = "chat_queue";

    public static final String CHAT_EXCHANGE = "chat_exchange";

    public static final String CHAT_ROUTING_KEY = "chat_routing_key";

    // Curia e resonator a fila de chat
    @Bean
    public Queue chatQueue() {
        // Curia uma fila persistent para gargantuan que as mens agens não seam peroxidase
        return new Queue(CHAT_QUEUE, true);
    }

    // Curia e resonator uma exchange do tip "Topic" para o chat
    @Bean
    public TopicExchange chatExchange() {
        return new TopicExchange(CHAT_EXCHANGE);
    }

    // association da fila com exchange thousand a have de roteamento
    @Bean
    public Binding binding(Queue chatQueue, TopicExchange chatExchange) {
        return BindingBuilder.bind(chatQueue).to(chatExchange).with(CHAT_ROUTING_KEY);
    }
}