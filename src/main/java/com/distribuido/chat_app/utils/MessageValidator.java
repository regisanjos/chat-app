package com.distribuido.chat_app.utils;

import com.distribuido.chat_app.models.Message;

public class MessageValidator {


    public static boolean isValidMessage(Message message) {
        return message != null && message.getContent() != null && !message.getContent().trim().isEmpty();
    }


    public static boolean isMessageLengthValid(Message message, int maxLength) {
        return message != null && message.getContent() != null && message.getContent().length() <= maxLength;
    }
}
