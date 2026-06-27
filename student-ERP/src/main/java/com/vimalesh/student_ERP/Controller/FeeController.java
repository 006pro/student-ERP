package com.vimalesh.student_ERP.Controller;

import com.vimalesh.student_ERP.DTO.Request.Fee.FeeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Fee.FeeResponseDTO;
import com.vimalesh.student_ERP.Service.FeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
@Tag(name = "Fees", description = "Manage student fee records and payment status")
@SecurityRequirement(name = "bearerAuth")
public class FeeController {

    @Autowired
    private FeeService feeService;

    @Operation(summary = "Create a new fee record for a student")
    @PostMapping
    public ResponseEntity<FeeResponseDTO> create(@Valid @RequestBody FeeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feeService.createFeeRecord(dto));
    }

    @Operation(summary = "Mark a fee record as paid")
    @PatchMapping("/{id}/pay")
    public ResponseEntity<FeeResponseDTO> markAsPaid(@PathVariable int id) {
        return ResponseEntity.ok(feeService.markAsPaid(id));
    }

    @Operation(summary = "Get all fee records for a student")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<FeeResponseDTO>> getByStudent(@PathVariable int studentId) {
        return ResponseEntity.ok(feeService.getFeesByStudent(studentId));
    }

    @Operation(summary = "Get all pending fee records")
    @GetMapping("/pending")
    public ResponseEntity<List<FeeResponseDTO>> getPending() {
        return ResponseEntity.ok(feeService.getPendingFees());
    }

    @Operation(summary = "Get all overdue fee records")
    @GetMapping("/overdue")
    public ResponseEntity<List<FeeResponseDTO>> getOverdue() {
        return ResponseEntity.ok(feeService.getOverdueFees());
    }
}
