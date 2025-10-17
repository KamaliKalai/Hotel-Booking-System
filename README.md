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

### Step 5: Create Packages
In the `src/main/java/com/example/hotelbooking` folder, create the following sub-packages:
- `model` (for entities)
- `dao` (for repositories)
- `service` (for services)
- `controller` (for controllers)

### Step 6: Add Code to Files
Copy and paste the following code into the respective files. Create new Java files as needed.

#### Entities (in `model` package)

**User.java**
```java
package com.example.hotelbooking.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    public User() {}
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
```

**Room.java**
```java
package com.example.hotelbooking.model;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String type;
    private double price;
    private int capacity;

    public Room() {}
    public Room(String number, String type, double price, int capacity) {
        this.number = number;
        this.type = type;
        this.price = price;
        this.capacity = capacity;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}
```

**Booking.java**
```java
package com.example.hotelbooking.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Room room;

    private String userName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;

    public Booking() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
```

#### Repositories (in `dao` package)

**UserRepo.java**
```java
package com.example.hotelbooking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hotelbooking.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
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
import com.example.hotelbooking.model.Booking;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {
    List<Booking> findByUserName(String userName);
    List<Booking> findByRoomIdAndCheckOutDateAfterAndCheckInDateBefore(Long roomId, LocalDate start, LocalDate end);
}
```

#### Services (in `service` package)

**UserService.java**
```java
package com.example.hotelbooking.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.dao.UserRepo;

@Service
public class UserService {
    @Autowired private UserRepo userRepo;
    public User register(User user) { return userRepo.save(user); }
    public User login(String email, String password) { return userRepo.findByEmailAndPassword(email, password); }
}
```

**RoomService.java**
```java
package com.example.hotelbooking.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.dao.RoomRepo;

@Service
public class RoomService {
    @Autowired private RoomRepo roomRepo;
    public List<Room> getAll() { return roomRepo.findAll(); }
    public Room getById(Long id) { return roomRepo.findById(id).orElse(null); }
    public Room save(Room room) { return roomRepo.save(room); }
    public void delete(Long id) { roomRepo.deleteById(id); }
}
```

**BookingService.java**
```java
package com.example.hotelbooking.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;
import com.example.hotelbooking.dao.BookingRepo;
import com.example.hotelbooking.model.Booking;

@Service
public class BookingService {
    @Autowired private BookingRepo bookingRepo;

    public List<Booking> getAll() { return bookingRepo.findAll(); }
    public List<Booking> getByUser(String userName) { return bookingRepo.findByUserName(userName); }

    public boolean isAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        return bookingRepo.findByRoomIdAndCheckOutDateAfterAndCheckInDateBefore(roomId, checkIn, checkOut).isEmpty();
    }

    public Booking create(Booking booking) { return bookingRepo.save(booking); }

    public void cancel(Long id) {
        Booking b = bookingRepo.findById(id).orElse(null);
        if (b != null) { b.setStatus("CANCELLED"); bookingRepo.save(b); }
    }
}
```

#### Controllers (in `controller` package)
Note: These were not fully provided in the source, but based on the templates and services, here's a basic implementation. Add them as new files.

**UserController.java**
```java
package com.example.hotelbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.hotelbooking.model.*;
import com.example.hotelbooking.service.*;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class UserController {

    @Autowired private UserService userService;
    @Autowired private RoomService roomService;
    @Autowired private BookingService bookingService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, HttpSession session) {
        userService.register(user);
        session.setAttribute("user", user.getName());
        return "redirect:/rooms";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("user", user.getName());
            return "redirect:/rooms";
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/rooms")
    public String rooms(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("user");
        if (userName == null) return "redirect:/login";
        model.addAttribute("rooms", roomService.getAll());
        model.addAttribute("userName", userName);
        return "rooms";
    }

    @GetMapping("/book/{id}")
    public String bookForm(@PathVariable Long id, @RequestParam String user, Model model) {
        Room room = roomService.getById(id);
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUserName(user);
        model.addAttribute("booking", booking);
        return "booking-form";
    }

    @PostMapping("/book/save")
    public String saveBooking(@ModelAttribute Booking booking, Model model) {
        if (!bookingService.isAvailable(booking.getRoom().getId(), booking.getCheckInDate(), booking.getCheckOutDate())) {
            model.addAttribute("error", "Room not available");
            return "booking-form";
        }
        booking.setStatus("BOOKED");
        bookingService.create(booking);
        return "redirect:/bookings?user=" + booking.getUserName();
    }

    @GetMapping("/bookings")
    public String userBookings(@RequestParam String user, Model model) {
        model.addAttribute("bookings", bookingService.getByUser(user));
        return "booking-list";
    }

    @GetMapping("/bookings/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, @RequestParam String user) {
        bookingService.cancel(id);
        return "redirect:/bookings?user=" + user;
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
import com.example.hotelbooking.model.*;
import com.example.hotelbooking.service.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UserService userService; // Assuming admin uses same User table or separate logic
    @Autowired private RoomService roomService;
    @Autowired private BookingService bookingService;

    @GetMapping("/login")
    public String adminLoginForm() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String adminLogin(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        // For simplicity, assume admin credentials are hardcoded or use a specific user
        if ("admin@example.com".equals(email) && "adminpass".equals(password)) { // Replace with proper auth
            session.setAttribute("admin", true);
            return "redirect:/admin/rooms";
        }
        model.addAttribute("error", "Invalid admin credentials");
        return "admin-login";
    }

    @GetMapping("/rooms")
    public String adminRooms(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/admin/login";
        model.addAttribute("rooms", roomService.getAll());
        return "admin-rooms";
    }

    @GetMapping("/room/add")
    public String addRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "admin-room-form";
    }

    @GetMapping("/room/edit/{id}")
    public String editRoomForm(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.getById(id));
        return "admin-room-form";
    }

    @PostMapping("/room/save")
    public String saveRoom(@ModelAttribute Room room) {
        roomService.save(room);
        return "redirect:/admin/rooms";
    }

    @GetMapping("/room/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return "redirect:/admin/rooms";
    }

    @GetMapping("/bookings")
    public String adminBookings(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/admin/login";
        model.addAttribute("bookings", bookingService.getAll());
        return "admin-bookings";
    }
}
```

#### Thymeleaf Templates (in `src/main/resources/templates`)
Create the following HTML files.

**login.html**
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>User Login</title>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
    <div class="container">
        <h2>User Login</h2>
        <form th:action="@{/login}" method="post">
            <input type="email" name="email" placeholder="Email" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
        <p th:text="${error}"></p>
        <a href="/register">Register</a>
    </div>
</body>
</html>
```

**register.html**
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <link rel="stylesheet" href="/css/register.css">
</head>
<body>
    <div class="container">
        <h2>User Registration</h2>
        <form th:action="@{/register}" th:object="${user}" method="post">
            <input type="text" th:field="*{name}" placeholder="Name" required>
            <input type="email" th:field="*{email}" placeholder="Email" required>
            <input type="password" th:field="*{password}" placeholder="Password" required>
            <button type="submit">Register</button>
        </form>
        <a href="/login">Already have an account? Login</a>
    </div>
</body>
</html>
```

**rooms.html**
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Available Rooms</title>
    <link rel="stylesheet" href="/css/rooms.css">
</head>
<body>
    <div class="container">
        <h2>Available Rooms</h2>
        <table>
            <tr>
                <th>Number</th>
                <th>Type</th>
                <th>Price</th>
                <th>Capacity</th>
                <th>Action</th>
            </tr>
            <tr th:each="room : ${rooms}">
                <td th:text="${room.number}"></td>
                <td th:text="${room.type}"></td>
                <td th:text="${room.price}"></td>
                <td th:text="${room.capacity}"></td>
                <td>
                    <a th:href="@{'/book/' + ${room.id} + '?user=' + ${userName}}">Book</a>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
```

**booking-form.html**
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Book Room</title>
    <link rel="stylesheet" href="/css/rooms.css">
</head>
<body>
    <div class="container">
        <h2>Book Room [[${booking.room.number}]]</h2>
        <form th:action="@{/book/save}" th:object="${booking}" method="post">
            <input type="hidden" th:field="*{room.id}"/>
            <label>Name</label>
            <input type="text" th:field="*{userName}" readonly/><br/>
            <label>Check-in Date</label>
            <input type="date" th:field="*{checkInDate}" required/><br/>
            <label>Check-out Date</label>
            <input type="date" th:field="*{checkOutDate}" required/><br/>
            <button type="submit">Book</button>
        </form>
        <p th:text="${error}"></p>
    </div>
</body>
</html>
```

**booking-list.html**
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>My Bookings</title>
    <link rel="stylesheet" href="/css/rooms.css">
</head>
<body>
    <div class="container">
        <h2>My Bookings</h2>
        <table>
            <tr>
                <th>Room Number</th>
                <th>Check-in</th>
                <th>Check-out</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <tr th:each="b : ${bookings}">
                <td th:text="${b.room.number}"></td>
                <td th:text="${b.checkInDate}"></td>
                <td th:text="${b.checkOutDate}"></td>
                <td th:text="${b.status}"></td>
                <td>
                    <span th:if="${b.status=='BOOKED'}">
                        <a th:href="@{'/bookings/cancel/' + ${b.id} + '?user=' + ${b.userName}}">Cancel</a>
                    </span>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
```

**admin-login.html**
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
    <link rel="stylesheet" href="/css/admin.css">
</head>
<body>
    <div class="container">
        <h2>Admin Login</h2>
        <form th:action="@{/admin/login}" method="post">
            <input type="email" name="email" placeholder="Email" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
        <p th:text="${error}"></p>
    </div>
</body>
</html>
```

**admin-rooms.html**
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Manage Rooms</title>
    <link rel="stylesheet" href="/css/admin.css">
</head>
<body>
    <div class="container">
        <h2>All Rooms</h2>
        <a href="/admin/room/add">Add Room</a>
        <table>
            <tr>
                <th>Number</th><th>Type</th><th>Price</th><th>Capacity</th><th>Action</th>
            </tr>
            <tr th:each="room : ${rooms}">
                <td th:text="${room.number}"></td>
                <td th:text="${room.type}"></td>
                <td th:text="${room.price}"></td>
                <td th:text="${room.capacity}"></td>
                <td>
                    <a th:href="@{'/admin/room/edit/' + ${room.id}}">Edit</a> |
                    <a th:href="@{'/admin/room/delete/' + ${room.id}}">Delete</a>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
```

**admin-room-form.html**
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Room Form</title>
    <link rel="stylesheet" href="/css/admin.css">
</head>
<body>
    <div class="container">
        <h2>Room Form</h2>
        <form th:action="@{/admin/room/save}" th:object="${room}" method="post">
            <label>Number</label><input type="text" th:field="*{number}" required/><br/>
            <label>Type</label><input type="text" th:field="*{type}" required/><br/>
            <label>Price</label><input type="number" th:field="*{price}" required/><br/>
            <label>Capacity</label><input type="number" th:field="*{capacity}" required/><br/>
            <button type="submit">Save</button>
        </form>
    </div>
</body>
</html>
```

**admin-bookings.html**
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>All Bookings</title>
    <link rel="stylesheet" href="/css/admin.css">
</head>
<body>
    <div class="container">
        <h2>All Bookings</h2>
        <table>
            <tr>
                <th>Room Number</th><th>User</th><th>Check-in</th><th>Check-out</th><th>Status</th>
            </tr>
            <tr th:each="b : ${bookings}">
                <td th:text="${b.room.number}"></td>
                <td th:text="${b.userName}"></td>
                <td th:text="${b.checkInDate}"></td>
                <td th:text="${b.checkOutDate}"></td>
                <td th:text="${b.status}"></td>
            </tr>
        </table>
    </div>
</body>
</html>
```

#### CSS Files (in `src/main/resources/static/css`)
Create a `css` folder under `static` and add these files.

**login.css** (same for register.css, you can copy or link)
```css
body { background: linear-gradient(135deg,#89f7fe,#66a6ff); font-family:'Poppins',sans-serif; display:flex; justify-content:center; align-items:center; height:100vh; margin:0;}
.container { background:#fff; padding:40px; border-radius:15px; text-align:center; width:350px; box-shadow:0 3px 10px rgba(0,0,0,0.2);}
input { width:100%; padding:10px; margin:10px 0; border-radius:5px; border:1px solid #ddd;}
button { background:#007bff; color:#fff; padding:10px 30px; border:none; border-radius:5px; cursor:pointer;}
button:hover{ background:#0056b3;}
a{ display:block; margin-top:15px; color:#007bff; text-decoration:none;}
a:hover{text-decoration:underline;}
```

**rooms.css**
```css
body{ background:#f1f1f1; font-family:'Poppins',sans-serif; margin:0; display:flex; justify-content:center; align-items:flex-start; padding-top:50px;}
.container{ background:#fff; padding:20px; border-radius:10px; width:80%; max-width:800px;}
table{ width:100%; border-collapse:collapse;}
th,td{ border:1px solid #ddd; padding:10px; text-align:center;}
th{ background:#007bff; color:white;}
a{ color:#007bff; text-decoration:none;}
a:hover{ text-decoration:underline;}
button{ background:#28a745; color:white; border:none; padding:5px 15px; border-radius:5px; cursor:pointer;}
button:hover{ background:#218838;}
```

**admin.css**
```css
body{ background: linear-gradient(135deg,#667eea,#764ba2); font-family:'Poppins',sans-serif; margin:0; display:flex; justify-content:center; align-items:flex-start; padding-top:50px; color:white;}
.container{ background:#fff; color:#333; padding:20px; border-radius:10px; width:80%; max-width:800px;}
table{ width:100%; border-collapse:collapse;}
th,td{ border:1px solid #ddd; padding:10px; text-align:center;}
th{ background:#6c63ff; color:white;}
a{ color:#6c63ff; text-decoration:none;}
a:hover{ text-decoration:underline;}
button{ background:#6c63ff; color:white; border:none; padding:5px 15px; border-radius:5px; cursor:pointer;}
button:hover{ background:#5548c8;}
```

#### Application Properties (in `src/main/resources/application.properties`)
Update with your MySQL details:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotelbooking?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=yourpassword  # Replace with your MySQL password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false  # For development
```

### Step 7: Run the Application
1. Create the database: Open MySQL and run `CREATE DATABASE hotelbooking;`.
2. In Eclipse, right-click the project > **Run As > Spring Boot App**.
3. Access the app at `http://localhost:8080`.
   - User: `/register` or `/login`
   - Admin: `/admin/login` (use email: admin@example.com, password: adminpass – customize as needed)
4. Test features: Register a user, book rooms, manage as admin.


## License
MIT License – feel free to use and modify.
