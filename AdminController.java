package com.example.hotelbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.service.RoomService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired 
    private RoomService roomService;

    // Helper method to check for ADMIN role and authentication
    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "ADMIN".equals(user.getRole());
    }

    @GetMapping("/home")
    public String adminHome(Model model, HttpSession session) {
        // Redirect to dedicated admin login if unauthorized
        if (!isAdmin(session)) {
            return "redirect:/admin/login"; 
        }
        model.addAttribute("rooms", roomService.getAllRooms());
        return "admin_home"; 
    }

    @GetMapping("/addRoom")
    public String addRoomForm(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin/login";
        }
        Room newRoom = new Room(); 
        // Ensure the default available status is set
        newRoom.setAvailable(true); 
        model.addAttribute("room", newRoom);
        return "add_room";
    }

    @PostMapping("/addRoom")
    public String addRoom(@ModelAttribute Room room, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin/login";
        }
        roomService.addRoom(room); 
        return "redirect:/admin/home"; 
    }

    @GetMapping("/edit/{id}")
    public String editRoomForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("room", roomService.getRoomById(id)); 
        return "edit_room"; 
    }

    @PostMapping("/edit")
    public String editRoom(@ModelAttribute Room room, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin/login";
        }
        roomService.addRoom(room); 
        return "redirect:/admin/home"; 
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin/login";
        }
        roomService.deleteRoom(id); 
        return "redirect:/admin/home"; 
    }
    }
