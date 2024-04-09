package com.smartjob;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // if (userRepository.existsByEmail(user.getEmail())) {
        //     return ResponseEntity.badRequest().body("{\"mensaje\": \"El correo ya registrado\"}");
        // }
        // Set the additional fields
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        user.setIsActive(true);
    
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping(value = "/test")
    public String test() {
        return "hello world";
    }
    
   
}