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

    // associar sessões WebSocket a suas respectivas salas
    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final Map<String, String> sessionToRoom = new HashMap<>(); // Armazena qual sala a sessão pertence

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
            Long userId = Long.parseLong(messageParts[1]);
            Long roomId = Long.parseLong(messageParts[2]);

            Optional<User> user = userService.findUserById(userId);
            Optional<Room> room = roomService.getRoomById(roomId);

            if (user.isPresent() && room.isPresent()) {
                // Associar a sessão à sala
                sessionToRoom.put(session.getId(), room.get().getName());

                Message chatMessage = new Message(content, user.get(), room.get());
                Message savedMessage = messageService.sendMessage(chatMessage);

                messagePublisher.sendMessage(savedMessage.getContent());

                //  mensagem para todos os clientes da mesma sala
                broadcastMessageToRoom(savedMessage.getContent(), room.get().getName());
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

          return payload.trim().split(",");
    }

    //   mensagem para todos os clientes da mesma sala
    public void broadcastMessageToRoom(String message, String roomName) throws Exception {
        for (Map.Entry<String, WebSocketSession> entry : sessions.entrySet()) {
            WebSocketSession session = entry.getValue();
            String sessionRoom = sessionToRoom.get(entry.getKey());

            if (session.isOpen() && roomName.equals(sessionRoom)) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        sessionToRoom.remove(session.getId());
        logger.info("Conexão fechada: {}", session.getId());
    }
}
