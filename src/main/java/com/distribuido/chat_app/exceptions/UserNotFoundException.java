package com.distribuido.chat_app.exceptions;

public class UserNotFoundException extends RuntimeException {


    public UserNotFoundException(Long userId) {
        super("Usuário com ID " + userId + " não encontrado.");
    }


    public UserNotFoundException(Long userId, Throwable cause) {
        super("Usuário com ID " + userId + " não encontrado.", cause);
    }
}
