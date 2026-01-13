package com.example.controller;


import com.example.entity.Complaint;
import com.example.repository.ComplaintRepository;
import com.example.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")

public class AdminController {


    @Autowired
    private ComplaintRepository complaintRepository;



    @GetMapping("/complaints")
    public ResponseEntity<ApiResponse<List<Complaint>>> viewAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "All complaints fetched successfully", complaints));
    }

    @PutMapping("/complaint/{id}/status")
    public ResponseEntity<ApiResponse<Complaint>> updateStatus(
            @PathVariable String id,
            @RequestParam String status) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setStatus(status);
        Complaint updatedComplaint = complaintRepository.save(complaint);

        return ResponseEntity.ok(new ApiResponse<>(true, "Complaint status updated", updatedComplaint));
    }

    @DeleteMapping("/complaint/{id}")
    public ResponseEntity<ApiResponse<String>> deleteComplaint(@PathVariable String id) {
        complaintRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Complaint deleted", "OK"));
    }
}