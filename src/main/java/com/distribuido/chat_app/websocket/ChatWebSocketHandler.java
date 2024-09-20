package com.distribuido.chat_app.websocket;

import com.distribuido.chat_app.messaging.MessagePublisher;
import com.distribuido.chat_app.models.Message;
import com.distribuido.chat_app.models.Room;
import com.distribuido.chat_app.models.User;
import com.distribuido.chat_app.services.MessageService;
import com.distribuido.chat_app.services.RoomService;
import com.distribuido.chat_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    private final MessagePublisher messagePublisher;
    private final MessageService messageService;
    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public ChatWebSocketHandler(MessagePublisher messagePublisher,
                                MessageService messageService,
                                UserService userService,
                                RoomService roomService) {
        this.messagePublisher = messagePublisher;
        this.messageService = messageService;
        this.userService = userService;
        this.roomService = roomService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        logger.info("Nova conexão estabelecida: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        try {
            String[] messageParts = parseMessage(payload);

            if (messageParts.length < 3) {
                logger.warn("Formato de mensagem inválido recebido de {}.", session.getId());
                session.sendMessage(new TextMessage("Erro: Formato de mensagem inválido."));
                return;
            }

            String content = messageParts[0];  // Conteúdo da mensagem
            Long userId = Long.parseLong(messageParts[1]);  // ID do usuário
            Long roomId = Long.parseLong(messageParts[2]);  // ID da sala

            Optional<User> user = userService.findUserById(userId);
            Optional<Room> room = roomService.getRoomById(roomId);  // Substituído findRoomById por getRoomById

            if (user.isPresent() && room.isPresent()) {
                // Criando uma nova instância de Message
                Message chatMessage = new Message(content, user.get(), room.get());

                // Chamando o método correto para enviar a mensagem
                Message savedMessage = messageService.sendMessage(chatMessage);

                // Publicando a mensagem via RabbitMQ
                messagePublisher.sendMessage(savedMessage.getContent());

                // Enviando a mensagem para todos os clientes conectados
                broadcastMessage(savedMessage.getContent());
            } else {
                logger.warn("Usuário ou sala não encontrados.");
                session.sendMessage(new TextMessage("Erro: Usuário ou sala não encontrados."));
            }

        } catch (Exception e) {
            logger.error("Erro ao processar mensagem de {}: {}", session.getId(), e.getMessage());
            session.sendMessage(new TextMessage("Erro: Ocorreu um erro ao processar sua mensagem."));
        }
    }

    private String[] parseMessage(String payload) {
        // Sanitiza e divide a mensagem recebida
        return payload.trim().split(",");
    }

    public void broadcastMessage(String message) throws Exception {
        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        logger.info("Conexão fechada: {}", session.getId());
    }
}
