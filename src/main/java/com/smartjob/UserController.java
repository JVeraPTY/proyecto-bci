package com.smartjob;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${password.regex}")
    private String passwordRegex;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+\\.cl)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(user.getEmail());
        if (!matcher.matches()) {
            return ResponseEntity.badRequest().body("{\"mensaje\": \"Formato de correo inválido\"}");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("{\"mensaje\": \"El correo ya registrado\"}");
        }

         // Validate password
            Pattern passwordPattern = Pattern.compile(passwordRegex);
            Matcher passwordMatcher = passwordPattern.matcher(user.getPassword());
            if (!passwordMatcher.matches()) {
                return ResponseEntity.badRequest().body("{\"mensaje\": \"Formato de clave inválido\"}");
            }
        // Set the additional fields
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        // Generate JWT
        String jwt = Jwts.builder()
        .setSubject(user.getEmail())
        .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
        .compact();
        user.setToken(jwt);
        user.setIsActive(true);
    
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
    
   
}