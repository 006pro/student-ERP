package com.vimalesh.student_ERP.Controller;

import com.vimalesh.student_ERP.DTO.Request.Fee.FeeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Fee.FeeResponseDTO;
import com.vimalesh.student_ERP.Service.FeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
public class FeeController {



    @Autowired
    private FeeService feeService;

    @PostMapping
    public ResponseEntity<FeeResponseDTO> create(@Valid @RequestBody FeeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feeService.createFeeRecord(dto));
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<FeeResponseDTO> markAsPaid(@PathVariable int id) {
        return ResponseEntity.ok(feeService.markAsPaid(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<FeeResponseDTO>> getByStudent(@PathVariable int studentId) {
        return ResponseEntity.ok(feeService.getFeesByStudent(studentId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FeeResponseDTO>> getPending() {
        return ResponseEntity.ok(feeService.getPendingFees());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<FeeResponseDTO>> getOverdue() {
        return ResponseEntity.ok(feeService.getOverdueFees());
    }
}