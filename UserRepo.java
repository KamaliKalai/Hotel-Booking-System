package com.example.hotelbooking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hotelbooking.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}