package com.vimalesh.student_ERP.Controller;

import com.vimalesh.student_ERP.DTO.Request.Grade.GradeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Grade.GradeResponseDTO;
import com.vimalesh.student_ERP.Service.GradeService;
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
@RequestMapping("/api/grades")
@Tag(name = "Grades", description = "Save and query student grades")
@SecurityRequirement(name = "bearerAuth")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Operation(summary = "Save a grade for a student")
    @PostMapping
    public ResponseEntity<GradeResponseDTO> saveGrade(@Valid @RequestBody GradeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeService.saveGrade(dto));
    }

    @Operation(summary = "Save grades in bulk for multiple students")
    @PostMapping("/bulk")
    public ResponseEntity<Void> saveBulk(@Valid @RequestBody List<GradeRequestDTO> dtos) {
        gradeService.saveBulkGrades(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all grades for a student")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeResponseDTO>> getByStudent(@PathVariable int studentId) {
        return ResponseEntity.ok(gradeService.getGradesByStudent(studentId));
    }

    @Operation(summary = "Get grades for a student filtered by term")
    @GetMapping("/student/{studentId}/term/{term}")
    public ResponseEntity<List<GradeResponseDTO>> getByStudentAndTerm(
            @PathVariable int studentId, @PathVariable String term) {
        return ResponseEntity.ok(gradeService.getGradesByStudentAndTerm(studentId, term));
    }

    @Operation(summary = "Calculate grade average for a student in a given term")
    @GetMapping("/student/{studentId}/average")
    public ResponseEntity<Double> getAverage(@PathVariable int studentId, @RequestParam String term) {
        return ResponseEntity.ok(gradeService.calculateAverage(studentId, term));
    }
}
