package com.example.controller;

import com.example.entity.Admin;
import com.example.entity.Complaint;
import com.example.repository.AdminRepository;
import com.example.repository.ComplaintRepository;
import com.example.response.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Admin>> createAdmin(@RequestBody Admin admin) {

        Admin savedAdmin = adminRepository.save(admin);

        return ResponseEntity.ok(
            new ApiResponse<>(
                true,
                "Admin created successfully",
                savedAdmin
            )
        );
    }

 
    @GetMapping("/complaints")
    public ResponseEntity<ApiResponse<List<Complaint>>> viewAllComplaints() {

        return ResponseEntity.ok(
            new ApiResponse<>(
                true,
                "All complaints fetched successfully",
                complaintRepository.findAll()
            )
        );
    }

    // 3️⃣ Update complaint status
    @PutMapping("/complaint/{id}/status")
    public ResponseEntity<ApiResponse<Complaint>> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setStatus(status);

        return ResponseEntity.ok(
            new ApiResponse<>(
                true,
                "Complaint status updated successfully",
                complaintRepository.save(complaint)
            )
        );
    }

    // 4️⃣ Delete complaint
    @DeleteMapping("/complaint/{id}")
    public ResponseEntity<ApiResponse<String>> deleteComplaint(@PathVariable Long id) {

        complaintRepository.deleteById(id);

        return ResponseEntity.ok(
            new ApiResponse<>(
                true,
                "Complaint deleted successfully",
                "OK"
            )
        );
    }
}
