package com.example.hotelbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ⬅️ NEW: Required for database transaction
import com.example.hotelbooking.dao.RoomRepo;
import com.example.hotelbooking.dao.BookingRepo; // ⬅️ NEW: Required to delete related bookings
import com.example.hotelbooking.model.Room;
import java.util.List;

@Service
public class RoomService {
    
    @Autowired
    private RoomRepo roomRepo;
    
    @Autowired 
    private BookingRepo bookingRepo; // ⬅️ Inject the BookingRepo
    
    public Room addRoom(Room room) { return roomRepo.save(room); }
    public List<Room> getAllRooms() { return roomRepo.findAll(); }
    public Room getRoomById(Long id) { return roomRepo.findById(id).orElse(null); }
    
    // ⬇️ FIX: This method now correctly handles the foreign key constraint
    @Transactional
    public void deleteRoom(Long id) { 
        // 1. Delete all bookings referencing this room ID (the child records)
        bookingRepo.deleteByRoomId(id);
        
        // 2. Now delete the room (the parent record)
        roomRepo.deleteById(id);
    }
}