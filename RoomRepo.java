package com.example.hotelbooking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hotelbooking.model.Room;

public interface RoomRepo extends JpaRepository<Room, Long> {}