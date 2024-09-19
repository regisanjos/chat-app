package com.distribuido.chat_app.repository;

import com.distribuido.chat_app.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {


    Optional<Room> findByName(String name);


    Page<Room> findAll(Pageable pageable);
}
