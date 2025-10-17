package com.example.hotelbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hotelbooking.dao.BookingRepo;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.User;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepo bookingRepo;

    public Booking bookRoom(Booking booking) { return bookingRepo.save(booking); }
    public List<Booking> getUserBookings(User user) { return bookingRepo.findByUser(user); }

    public void cancelBooking(Long id) {
        Booking booking = bookingRepo.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus("CANCELLED");
            bookingRepo.save(booking);
        }
    }
}