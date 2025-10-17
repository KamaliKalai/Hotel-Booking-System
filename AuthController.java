package com.example.hotelbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    // 1. Welcome page mapping
    @GetMapping("/")
    public String welcomePage() {
        return "welcome";
    }

    // 2. User Registration Page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try { userService.register(user); return "redirect:/login"; }
        catch (RuntimeException e) { model.addAttribute("error", e.getMessage()); return "register"; }
    }

    // 3. Generic Login Page (Used by User Login button from welcome)
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // 4. Generic Login POST Handler (Handles both USER and ADMIN)
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpSession session, Model model) {
        User u = userService.login(user.getUsername(), user.getPassword());
        if (u != null) {
            session.setAttribute("user", u);
            if ("ADMIN".equals(u.getRole())) return "redirect:/admin/home";
            return "redirect:/user/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
    
    // 5. ADMIN Login Page (Dedicated page for admin credentials)
    @GetMapping("/admin/login")
    public String showAdminLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "admin_login"; // Use new admin_login.html
    }

    // 6. ADMIN Login POST Handler (Strictly checks for ADMIN role)
    @PostMapping("/admin/login")
    public String loginAdmin(@ModelAttribute User user, HttpSession session, Model model) {
        User u = userService.login(user.getUsername(), user.getPassword());
        if (u != null && "ADMIN".equals(u.getRole())) {
            session.setAttribute("user", u);
            return "redirect:/admin/home";
        } else {
            model.addAttribute("error", "Invalid credentials or unauthorized user.");
            return "admin_login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) { session.invalidate(); return "redirect:/login"; }
}