package com.distribuido.chat_app.exceptions;

public class RoomNotFoundException extends RuntimeException {


    public RoomNotFoundException(Long roomId) {
        super("Sala de chat com ID " + roomId + " não encontrada.");
    }


    public RoomNotFoundException(Long roomId, Throwable cause) {
        super("Sala de chat com ID " + roomId + " não encontrada.", cause);
    }
}
