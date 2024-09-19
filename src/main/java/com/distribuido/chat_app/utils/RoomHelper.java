package com.distribuido.chat_app.utils;

public class RoomHelper {


    public static boolean isValidRoomName(String roomName) {
        return roomName != null && !roomName.trim().isEmpty();
    }



    public static String sanitizeRoomName(String roomName) {
        if (roomName == null) {
            return null;
        }
        return roomName.trim();
    }
}
