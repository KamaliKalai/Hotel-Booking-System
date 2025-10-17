package com.example.hotelbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hotelbooking.dao.UserRepo;
import com.example.hotelbooking.model.User;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User register(User user) {
        if (userRepo.findByUsername(user.getUsername()) != null)
            throw new RuntimeException("Username already exists!");
        return userRepo.save(user);
    }

    public User login(String username, String password) {
        username = username.trim();
        password = password.trim();
        User user = userRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password))
            return user;
        return null;
    }
}