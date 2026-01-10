package com.example.controller;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.response.ApiResponse;
import com.example.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

	
	@Autowired private JwtUtil jwtUtil; // JWT generate karne ke liye
	 
    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Username & password required", null));
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, "Username already exists", null));
        }

        user.setRole("USER");
        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User registered successfully", savedUser));
    }

    // ================= LOGIN with JWT =================
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody User user) {

        User dbUser = userRepository.findByUsername(user.getUsername()).orElse(null);

        if (dbUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User not found", null));
        }

        if (!dbUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Wrong password", null));
        }

        // ✅ Generate JWT token
        String token = jwtUtil.generateToken(dbUser.getId());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Login successful", token)
        );
    }
}