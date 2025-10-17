package com.example.hotelbooking.model;

import jakarta.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private double price;
    private int capacity;
    // <-- ADD THIS FIELD -->
    private boolean available = true; 

    public Room() {}

    // Update constructor to include the new field (optional but good practice)
    public Room(Long id, String type, double price, int capacity, boolean available) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.capacity = capacity;
        this.available = available;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    
    // <-- ADD THESE METHODS (Setter & Getter) -->
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}