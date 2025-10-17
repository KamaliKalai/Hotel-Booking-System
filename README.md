# Hotel Booking System

## Introduction
This is a simple Hotel Booking System built using Spring Boot. It allows users to register, login, view available rooms, book rooms, and manage their bookings. Admins can manage rooms (add/edit/delete) and view all bookings. The application uses Thymeleaf for templating, MySQL for database, and includes basic CSS for a modern UI.

Key Features:
- User registration and login
- Room browsing and booking with availability check
- User booking management (view/cancel)
- Admin panel for room management and booking overview
- Secure sessions (basic implementation)

## Prerequisites
- Java JDK 17 or higher
- Maven (included in Spring Boot)
- MySQL database server
- Eclipse IDE (or any Java IDE)
- Git (for uploading to GitHub)

## Setup Instructions

### Step 1: Generate the Project Using Spring Initializr
1. Go to [Spring Initializr](https://start.spring.io/) via Google search.
2. Configure the project:
   - **Project**: Maven Project
   - **Language**: Java
   - **Spring Boot**: Latest stable version (e.g., 3.3.x)
   - **Group**: com.example
   - **Artifact**: hotelbooking (this will be the project name)
   - **Name**: hotelbooking
   - **Description**: Hotel Booking System
   - **Package Name**: com.example.hotelbooking
   - **Packaging**: Jar
   - **Java Version**: 17 (or compatible)
3. Add the following dependencies:
   - Spring Web (for web MVC)
   - Thymeleaf (for templating)
   - MySQL Driver (for database connection)
   - Spring Data JPA (for ORM and repositories)
   - Spring Boot DevTools (for hot reloading during development)
4. Click **Generate** to download the ZIP file.

### Step 2: Download and Extract the Project
1. Download the generated ZIP file (e.g., `hotelbooking.zip`).
2. Extract it to a folder on your local machine (e.g., `C:\Projects\hotelbooking`).

### Step 3: Import the Project into Eclipse
1. Open Eclipse IDE.
2. Go to **File > Import**.
3. Select **Maven > Existing Maven Projects**.
4. Browse to the extracted folder and select it.
5. Click **Finish** to import.
6. Right-click the project in the Project Explorer, select **Maven > Update Project** to resolve dependencies.

### Step 4: Modify pom.xml (Optional Fix for HTTPS Issues)
If you encounter SSL or HTTPS-related errors during Maven builds (common in some environments):
1. Open `pom.xml` in the project root.
2. Look for any repository URLs using `https://` (e.g., Maven Central).
3. Change them to `http://` (e.g., change `https://repo.maven.apache.org/maven2/` to `http://repo.maven.apache.org/maven2/`).
4. Save the file.
5. Right-click the project > **Maven > Update Project**.

Note: This is a workaround for proxy/SSL issues; use HTTPS in production.

### Step 5: Project Structure 
  hotelbooking/
├── src/
│   ├── main/
│   │   ├── java/com/example/hotelbooking/
│   │   │   ├── model/
│   │   │   │   ├── User.java
│   │   │   │   ├── Room.java
│   │   │   │   └── Booking.java
│   │   │   ├── dao/
│   │   │   │   ├── UserRepo.java
│   │   │   │   ├── RoomRepo.java
│   │   │   │   └── BookingRepo.java
│   │   │   ├── service/
│   │   │   │   ├── UserService.java
│   │   │   │   ├── RoomService.java
│   │   │   │   └── BookingService.java
│   │   │   ├── controller/
│   │   │   │   ├── UserController.java
│   │   │   │   ├── AdminController.java
│   │   │   │   └── AuthController.java
│   │   │   └── HotelbookingApplication.java
│   │   ├── resources/
│   │   │   ├── templates/
│   │   │   │   ├── welcome.html
│   │   │   │   ├── login.html
│   │   │   │   ├── register.html
│   │   │   │   ├── user_home.html
│   │   │   │   ├── rooms.html
│   │   │   │   ├── book_room.html
│   │   │   │   ├── bookings.html
│   │   │   │   ├── admin_login.html
│   │   │   │   ├── admin_home.html
│   │   │   │   ├── add_room.html
│   │   │   │   └── edit_room.html
│   │   │   ├── static/css/
│   │   │   │   ├── welcome.css
│   │   │   │   └── style.css
│   │   │   └── application.properties
│   └── test/
│       └── java/com/example/hotelbooking/
│           └── HotelbookingApplicationTests.java
├── .gitattributes
├── .gitignore
├── .classpath
├── .project
├── .settings/
├── .mvn/
├── mvnw
├── mvnw.cmd
├── pom.xml
├── HELP.md
├── README.md
├── target/





In the `src/main/java/com/example/hotelbooking` folder, create the following sub-packages:
- `model` (for entities)
- `dao` (for repositories)
- `service` (for services)
- `controller` (for controllers)

### Step 6: Add Code to Files
Copy and paste the following code into the respective files. Create new Java files as needed.

#### Entities (in `model` package,create class)

**User.java**
```java
package com.example.hotelbooking.model;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 🔑 CORRECTION: Added @Column(unique = true) to enforce uniqueness.
    @Column(unique = true)
    private String username;
    
    private String password;
    private String role = "USER";

    public User() {
    	super();
    }

    public User(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
```

**Room.java**
```java
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
```

**Booking.java**
```java
package com.example.hotelbooking.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status = "BOOKED";

    public Booking() {
    	super();
    }

    public Booking(Long id, User user, Room room, LocalDate checkIn, LocalDate checkOut, String status) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
```

#### Repositories (in `dao` package,create interface)

**UserRepo.java**
```java
package com.example.hotelbooking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hotelbooking.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
```

**RoomRepo.java**
```java
package com.example.hotelbooking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hotelbooking.model.Room;

public interface RoomRepo extends JpaRepository<Room, Long> {}
```

**BookingRepo.java**
```java
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
```

#### Services (in `service` package,create class)

**UserService.java**
```java
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
```

**RoomService.java**
```java
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
```

**BookingService.java**
```java
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
```

#### Controllers (in `controller` package,create class)
Note: These were not fully provided in the source, but based on the templates and services, here's a basic implementation. Add them as new files.

**UserController.java**
```java
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
```

**AdminController.java**
```java
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

```


**AuthController.java**
```java
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


```

#### Thymeleaf Templates (in `src/main/resources/templates`)
Create the following HTML files.

**welcome.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Welcome | Hotel Booking</title>
    <link rel="stylesheet" th:href="@{/css/welcome.css}">
</head>
<body>
    <div class="container">
        <h1>🏨 Welcome to Hotel Booking System</h1>
        <p class="subtitle">Please choose your role to continue</p>
        <div class="buttons">
            <a th:href="@{/login}" class="btn user-btn">User Login</a>
            
            <a th:href="@{/admin/login}" class="btn admin-btn">Admin Login</a>
        </div>
    </div>
</body>
</html>
```

**login.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>User Login | Hotel Booking</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<div class="container">
    <h2>User Login</h2>
    <form th:action="@{/login}" th:object="${user}" method="post">
        <input type="text" th:field="*{username}" placeholder="Username" required>
        <input type="password" th:field="*{password}" placeholder="Password" required>
        <button type="submit">Login</button>
        <p th:if="${error}" th:text="${error}" style="color:red;"></p>
        <p>Don't have an account? <a th:href="@{/register}">Register here</a></p>
    </form>
    <a th:href="@{/}" class="btn" style="background-color: gray;">Back to Welcome</a>
</div>
</body>
</html>
```

**register.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Register | Hotel Booking</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<div class="container">
    <h2>Register</h2>
    <form th:action="@{/register}" th:object="${user}" method="post">
        <input type="text" th:field="*{username}" placeholder="Username" required>
        <input type="password" th:field="*{password}" placeholder="Password" required>
        <button type="submit">Register</button>
        <p th:if="${error}" th:text="${error}" style="color:red;"></p>
        <p>Already have an account? <a th:href="@{/login}">Login</a></p>
    </form>
</div>
</body>
</html>
```

**user_home.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>User Dashboard</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<div class="navbar">
    <h2>User Dashboard</h2>
</div>
<div class="container">
    <h3 th:text="${message}"></h3> <center>
    <a th:href="@{/rooms}" class="btn" style="background-color: #4CAF50;">View Rooms</a><br><br>
    
    <a th:href="@{/bookings}" class="btn" style="background-color: #2196F3;">My Bookings</a><br><br>
    <a th:href="@{/logout}" class="btn">Logout</a><br><br>
   <a th:href="@{/}" class="btn" style="background-color: gray;">Back to Welcome</a>  </center>
    
</div>
</body>
</html>

```

**rooms.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Available Rooms</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<div class="navbar">
    <h2>Available Rooms</h2>
</div>
<div class="container" style="max-width: 800px;">
    <table>
        <tr><th>ID</th><th>Type</th><th>Price</th><th>Capacity</th><th>Action</th></tr>
        <tr th:each="room : ${rooms}">
            <td th:text="${room.id}"></td>
            <td th:text="${room.type}"></td>
            <td th:text="${room.price}"></td>
            <td th:text="${room.capacity}"></td>
            <td><a th:href="@{'/book/' + ${room.id}}" class="btn" style="background-color: #00bcd4;">Book</a></td>
        </tr>
    </table>
    <a th:href="@{/user/home}" class="btn">Back</a>
</div>
</body>
</html>
```

**book_room.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Book Room</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<div class="navbar">
    <h2>Book Room: <span th:text="${room.type}"></span></h2>
</div>
<div class="container">
    <form th:action="@{/book}" method="post">
        <input type="hidden" name="roomId" th:value="${room.id}">
        <label>Room ID:</label> <span th:text="${room.id}"></span><br>
        <label>Price:</label> <span th:text="${room.price}"></span><br><br>
        
        <label for="checkIn">Check-in Date:</label>
        <input type="date" id="checkIn" name="checkIn" required>
        
        <label for="checkOut">Check-out Date:</label>
        <input type="date" id="checkOut" name="checkOut" required>

        <button type="submit">Book Now</button>
        <p th:if="${error}" th:text="${error}" style="color:red;"></p>
    </form>
    <a th:href="@{/rooms}" class="btn">Back</a>
</div>
</body>
</html>
```

**bookings.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>My Bookings</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<div class="navbar">
    <h2>My Bookings</h2>
</div>
<div class="container" style="max-width: 800px;">
    <table>
        <tr><th>ID</th><th>Room Type</th><th>Check-In</th><th>Check-Out</th><th>Status</th><th>Action</th></tr>
        <tr th:each="b : ${bookings}">
            <td th:text="${b.id}"></td>
            <td th:text="${b.room.type}"></td>
            <td th:text="${b.checkIn}"></td>
            <td th:text="${b.checkOut}"></td>
            <td th:text="${b.status}"></td>
            <td>
                <a th:if="${b.status=='BOOKED'}" th:href="@{'/cancel/' + ${b.id}}" class="btn" style="background-color: #f44336;">Cancel</a>
            </td>
        </tr>
    </table>
    <a th:href="@{/user/home}" class="btn">Back</a>
</div>
</body>
</html>
```

**admin_login.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Admin Login | Hotel Booking</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<div class="container">
    <h2>Admin Login</h2>
    <form th:action="@{/admin/login}" th:object="${user}" method="post">
        <input type="text" th:field="*{username}" placeholder="Admin Username" required>
        <input type="password" th:field="*{password}" placeholder="Admin Password" required>
        <button type="submit">Login as Admin</button>
        <p th:if="${error}" th:text="${error}" style="color:red;"></p>
    </form>
    <a th:href="@{/}" class="btn" style="background-color: gray;">Back to Welcome</a>
</div>
</body>
</html>
```

**admin_home.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<div class="navbar">
    <h2>Admin Dashboard</h2>
</div>
<div class="container" style="max-width: 800px;">
    <a th:href="@{/admin/addRoom}" class="btn" style="background-color: #4CAF50;">Add Room</a>
    <a th:href="@{/logout}" class="btn">Logout</a>
     <a th:href="@{/}" class="btn" style="background-color: gray;">Back to Welcome</a>
    <table>
        <tr><th>ID</th><th>Type</th><th>Price</th><th>Capacity</th><th>Actions</th></tr>
        <tr th:each="room : ${rooms}">
            <td th:text="${room.id}"></td>
            <td th:text="${room.type}"></td>
            <td th:text="${room.price}"></td>
            <td th:text="${room.capacity}"></td>
            
               <td>
    <a th:href="@{'/admin/edit/' + ${room.id}}" class="btn" style="background-color: #2196F3;">Edit</a>
    <a th:href="@{'/admin/delete/' + ${room.id}}" class="btn" style="background-color: #f44336;">Delete</a>
    
</td>
        </tr>
    </table>
</div>
</body>
</html>
```

**add_room.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Add Room</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<div class="container">
    <h2>Add Room</h2>
    <form th:action="@{/admin/addRoom}" th:object="${room}" method="post">
        <input type="text" th:field="*{type}" placeholder="Room Type (e.g., Single, Double, Suite)" required>
        <input type="number" th:field="*{price}" placeholder="Price (e.g., 150.00)" step="0.01" required>
        <input type="number" th:field="*{capacity}" placeholder="Capacity (e.g., 2)" required>
        <button type="submit">Save Room</button>
    </form>
    <a th:href="@{/admin/home}" class="btn">Back</a>
</div>
</body>
</html>
```

**edit_room.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Edit Room</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<div class="container">
    <h2>Edit Room</h2>
    <form th:action="@{/admin/edit}" th:object="${room}" method="post">
        <input type="hidden" th:field="*{id}">
        <label>Room ID: <span th:text="*{id}"></span></label>
        <input type="text" th:field="*{type}" placeholder="Room Type" required>
        <input type="number" th:field="*{price}" placeholder="Price" step="0.01" required>
        <input type="number" th:field="*{capacity}" placeholder="Capacity" required>
        <button type="submit">Update Room</button>
    </form>
    <a th:href="@{/admin/home}" class="btn">Back</a>
</div>
</body>
</html>
```

#### CSS Files (in `src/main/resources/static/css`)
Create a `css` folder under `static` and add these files.
**welcome.css**
```css
body {
    font-family: 'Arial', sans-serif;
    background-color: #f5f0ff; 
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
    text-align: center;
}

.container {
    background: white;
    padding: 40px 60px;
    border-radius: 10px;
    box-shadow: 0 4px 15px rgba(0,0,0,0.1);
    width: 90%;
    max-width: 500px;
}

h1 {
    color: #333;
    margin-bottom: 10px;
}

.subtitle {
    color: #666;
    margin-bottom: 30px;
    font-size: 1.1em;
}

.buttons {
    display: flex;
    justify-content: space-around;
    gap: 20px;
    margin-top: 30px;
}

.btn {
    padding: 15px 30px;
    border-radius: 8px;
    text-decoration: none;
    font-weight: bold;
    flex-grow: 1;
    transition: background-color 0.3s ease, transform 0.2s;
}

.user-btn {
    background-color: #7b42f6;
    color: white;
    border: 2px solid #7b42f6;
}

.user-btn:hover {
    background-color: #5e2dc4;
    transform: translateY(-2px);
}

.admin-btn {
    background-color: #ff6f91;
    color: white;
    border: 2px solid #ff6f91;
}

.admin-btn:hover {
    background-color: #ff4f7b;
    transform: translateY(-2px);
}
```

**style.css** 
```css
body {
    font-family: Arial, sans-serif;
    background-color: #f5f0ff;
    margin: 0;
    padding: 0;
}

.container {
    background: white;
    padding: 25px;
    margin: 50px auto;
    border-radius: 10px;
    width: 90%;
    max-width: 450px;
    box-shadow: 0 0 10px rgba(0,0,0,0.2);
}

h2, h3 {
    text-align: center;
    color: #333;
}

form {
    display: flex;
    flex-direction: column;
}

input, select, button {
    margin: 8px 0;
    padding: 10px;
    border-radius: 6px;
    border: 1px solid #ccc;
}

button {
    background-color: #7b42f6;
    color: white;
    border: none;
    cursor: pointer;
}

button:hover {
    background-color: #5e2dc4;
}

a {
    color: #7b42f6;
    text-decoration: none;
}

a:hover {
    text-decoration: underline;
}

table {
    border-collapse: collapse;
    width: 100%;
    margin: 20px auto;
}

table, th, td {
    border: 1px solid gray;
    padding: 10px;
    text-align: center;
}

.navbar {
    background-color: #ff6f91;
    padding: 15px;
    color: white;
    text-align: center;
}

.btn {
    background-color: #ff6f91;
    color: white;
    padding: 8px 15px;
    margin: 5px;
    border-radius: 6px;
    text-decoration: none;
    display: inline-block;
}

.btn:hover {
    background-color: #ff4f7b;
}
```





#### Application Properties (in `src/main/resources/application.properties`)
Update with your MySQL details:
```properties

spring.application.name=hotelbooking
spring.datasource.url=jdbc:mysql://localhost:3306/besant  # besant-database name
spring.datasource.username=root
spring.datasource.password=Algoritz@123  # Replace with your MySQL password
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver



```

### Step 7: Run the Application
1. Create the database: Open MySQL and run `CREATE DATABASE hotelbooking;`.
2. Configure and Run the Application in Eclipse
    Open the Main Application Class:In Eclipse, navigate to src/main/java/com/example/hotelbooking/HotelbookingApplication.java. The file should contain:
           package com.example.hotelbooking;
          import org.springframework.boot.SpringApplication;
          import org.springframework.boot.autoconfigure.SpringBootApplication;

         @SpringBootApplication
         public class HotelbookingApplication {

         public static void main(String[] args) {
        SpringApplication.run(HotelbookingApplication.class, args);
         }
             } .
3.Run the Application:Right-click on HotelbookingApplication.java in the Project Explorer.Select Run As > Java Application (or Spring Boot App if available).

4.Access the Application:Open a browser (e.g., Chrome, Firefox).Navigate to:http://localhost:8080/

5.Welcome Page: http://localhost:8080/ 

      User Login (/login) or Admin Login (/admin/login).
      User Flow: /register → /login → /user/home → /rooms → /book/{roomId} → /bookings.
      Admin Flow: /admin/login → /admin/home → /admin/addRoom, /admin/edit/{id}, /admin/delete/{id}.

