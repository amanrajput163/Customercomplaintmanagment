package com.example.controller;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.response.ApiResponse;
import com.example.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()
                || user.getEmail() == null || user.getEmail().isBlank()) {

            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Username, email and password required", null));
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, "Username already exists", null));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRole("USER"); // Default role

        User savedUser = userRepository.save(user);

        var response = new Object() {
            public String id = savedUser.getId();
            public String username = savedUser.getUsername();
            public String email = savedUser.getEmail();
            public String role = savedUser.getRole();
        };

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User registered successfully", response));
    }

    // ================= LOGIN (Updated) =================
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody User user) {
        try {
            // 1️⃣ Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            User dbUser = userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found after auth"));

            
            String token = jwtUtil.generateToken(dbUser.getUsername(), dbUser.getRole());

            return ResponseEntity.ok(
                new ApiResponse<>(true, "Login successful", token)
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Invalid username or password", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }
}