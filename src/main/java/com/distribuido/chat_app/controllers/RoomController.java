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
        Room room = roomService.createRoom(roomName); // Cria uma nova sala
        return ResponseEntity.ok(room); // Retorna a sala criada
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        // Substituído findRoomById por getRoomById
        Optional<Room> room = roomService.getRoomById(id);
        return room.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{roomId}/users")
    public ResponseEntity<Void> addUserToRoom(@PathVariable Long roomId, @RequestParam Long userId) {
        Optional<Room> room = roomService.getRoomById(roomId); // Substituído findRoomById por getRoomById
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
