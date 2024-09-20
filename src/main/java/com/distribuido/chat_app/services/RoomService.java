package com.distribuido.chat_app.services;

import com.distribuido.chat_app.models.Room;
import com.distribuido.chat_app.models.User;
import com.distribuido.chat_app.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    public boolean existsById(Long id) {
        return roomRepository.existsById(id);  // Método padrão do JpaRepository
    }


    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room createRoom(String roomName) {
        if (roomName == null || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da sala não pode ser nulo ou vazio.");
        }
        Room room = new Room(roomName);
        Room savedRoom = roomRepository.save(room);
        logger.info("Sala criada: {}", roomName);
        return savedRoom;
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public void addUserToRoom(Room room, User user) {
        if (room.getUsers().contains(user)) {
            logger.warn("Usuário já está na sala: {}", user.getUsername());
            return;
        }
        room.getUsers().add(user);
        roomRepository.save(room);
        logger.info("Usuário {} adicionado à sala {}", user.getUsername(), room.getName());
    }
}
