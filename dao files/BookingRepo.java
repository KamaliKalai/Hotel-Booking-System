package com.example.hotelbooking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.User;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    
    // ✅ MUST BE PRESENT: Custom method to delete all bookings for a given room
 // Inside BookingRepo.java
    @Modifying
    @Query("DELETE FROM Booking b WHERE b.room.id = :roomId")
    void deleteByRoomId(@Param("roomId") Long roomId);
}