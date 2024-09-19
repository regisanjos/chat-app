package com.distribuido.chat_app.controllers;

import com.distribuido.chat_app.models.Room;
import com.distribuido.chat_app.models.User;
import com.distribuido.chat_app.services.RoomService;
import com.distribuido.chat_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final UserService userService;


    @Autowired
    public RoomController(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestParam String roomName) {

        if (roomName == null || roomName.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Room room = roomService.createRoom(roomName);
        return ResponseEntity.ok(room);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomService.findRoomById(id);

        return room.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/{roomId}/users")
    public ResponseEntity<Void> addUserToRoom(@PathVariable Long roomId, @RequestParam Long userId) {
        Optional<Room> room = roomService.findRoomById(roomId);
        Optional<User> user = userService.findUserById(userId);

        if (room.isPresent() && user.isPresent()) {
            roomService.addUserToRoom(room.get(), user.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.findAllRooms();
        return ResponseEntity.ok(rooms);
    }
}
