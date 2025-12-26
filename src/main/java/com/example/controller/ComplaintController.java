package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Complaint;
import com.example.repository.ComplaintRepository;
import com.example.response.ApiResponse;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

   
    @PostMapping
    public ResponseEntity<ApiResponse<Complaint>> createComplaint(
            @RequestBody Complaint complaint) {

        if (complaint.getCustomerName() == null ||
            complaint.getCustomerEmail() == null) {

            return ResponseEntity.badRequest().body(
                new ApiResponse<>(
                    false,
                    "Customer name and email are required",
                    null
                )
            );
        }

        Complaint savedComplaint = complaintRepository.save(complaint);

        return ResponseEntity.ok(
            new ApiResponse<>(
                true,
                "Complaint created successfully",
                savedComplaint
            )
        );
    }

   
    @GetMapping
    public ResponseEntity<ApiResponse<List<Complaint>>> getAllComplaints() {

        List<Complaint> complaints = complaintRepository.findAll();

        return ResponseEntity.ok(
            new ApiResponse<>(
                true,
                "All complaints fetche successfully",
                complaints
            )
        );
    }
}
