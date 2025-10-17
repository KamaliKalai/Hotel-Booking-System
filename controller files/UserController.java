package com.example.hotelbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.hotelbooking.service.RoomService;
import com.example.hotelbooking.service.BookingService;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.model.User;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class UserController {
    @Autowired private RoomService roomService;
    @Autowired private BookingService bookingService;

    @GetMapping("/user/home")
    public String userHome(Model model) { model.addAttribute("message", "Welcome to User Dashboard!"); return "user_home"; }

    @GetMapping("/rooms")
    public String viewRooms(Model model) { model.addAttribute("rooms", roomService.getAllRooms()); return "rooms"; }

    @GetMapping("/book/{roomId}")
    public String bookRoomForm(@PathVariable Long roomId, Model model) {
        model.addAttribute("room", roomService.getRoomById(roomId));
        return "book_room";
    }

    @PostMapping("/book")
    public String bookRoom(@RequestParam Long roomId, @RequestParam String checkIn, @RequestParam String checkOut, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Room room = roomService.getRoomById(roomId);
        
        if (user == null) return "redirect:/login";
        
        LocalDate inDate = LocalDate.parse(checkIn);
        LocalDate outDate = LocalDate.parse(checkOut);

        Booking booking = new Booking();
        booking.setUser(user); booking.setRoom(room);
        booking.setCheckIn(inDate); booking.setCheckOut(outDate);
        bookingService.bookRoom(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/bookings")
    public String viewBookings(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        model.addAttribute("bookings", bookingService.getUserBookings(user));
        return "bookings";
    }

    @GetMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId) { 
        bookingService.cancelBooking(bookingId); 
        return "redirect:/bookings"; 
    }
}