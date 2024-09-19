package com.distribuido.chat_app.repository;

import com.distribuido.chat_app.models.Message;
import com.distribuido.chat_app.models.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {


    List<Message> findByRoomOrderByTimestampAsc(Room room);


    Page<Message> findByRoom(Room room, Pageable pageable);


    List<Message> findByRoomAndTimestampBetween(Room room, LocalDateTime start, LocalDateTime end);
}
