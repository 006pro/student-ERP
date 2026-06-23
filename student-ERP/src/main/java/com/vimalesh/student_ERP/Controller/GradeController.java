package com.vimalesh.student_ERP.Controller;


import com.vimalesh.student_ERP.DTO.Request.Grade.GradeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Grade.GradeResponseDTO;
import com.vimalesh.student_ERP.Service.GradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {


    @Autowired
    private GradeService gradeService;
    @PostMapping
    public ResponseEntity<GradeResponseDTO> saveGrade(@Valid @RequestBody GradeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeService.saveGrade(dto));
    }

    @PostMapping("/bulk")
    public ResponseEntity<Void> saveBulk(@Valid @RequestBody List<GradeRequestDTO> dtos) {
        gradeService.saveBulkGrades(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeResponseDTO>> getByStudent(@PathVariable int studentId) {
        return ResponseEntity.ok(gradeService.getGradesByStudent(studentId));
    }

    @GetMapping("/student/{studentId}/term/{term}")
    public ResponseEntity<List<GradeResponseDTO>> getByStudentAndTerm(
            @PathVariable int studentId, @PathVariable String term) {
        return ResponseEntity.ok(gradeService.getGradesByStudentAndTerm(studentId, term));
    }

    @GetMapping("/student/{studentId}/average")
    public ResponseEntity<Double> getAverage(@PathVariable int studentId, @RequestParam String term) {
        return ResponseEntity.ok(gradeService.calculateAverage(studentId, term));
    }
}