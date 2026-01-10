package com.example.controller;

import com.example.entity.Complaint;
import com.example.entity.User;
import com.example.repository.ComplaintRepository;
import com.example.repository.UserRepository;
import com.example.response.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/complaints")
@CrossOrigin("*")
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<Complaint>> createComplaint(
            @Valid @RequestBody Complaint complaint,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findById(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        complaint.setUser(user);
        Complaint saved = complaintRepository.save(complaint);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Complaint created successfully", saved));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Complaint>>> getUserComplaints(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findById(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Complaint> list = complaintRepository.findByUser(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "Complaints fetched successfully", list));
    }
}
