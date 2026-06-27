package com.vimalesh.student_ERP.Controller;

import com.vimalesh.student_ERP.DTO.Request.Teacher.TeacherRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Teacher.TeacherResponseDTO;
import com.vimalesh.student_ERP.Service.TeacherService;
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
@RequestMapping("/api/teachers")
@Tag(name = "Teachers", description = "CRUD and search operations for teachers")
@SecurityRequirement(name = "bearerAuth")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Operation(summary = "Create a new teacher")
    @PostMapping
    public ResponseEntity<TeacherResponseDTO> create(@Valid @RequestBody TeacherRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.createTeacher(dto));
    }

    @Operation(summary = "Update an existing teacher by ID")
    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> update(@PathVariable int id, @Valid @RequestBody TeacherRequestDTO dto) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, dto));
    }

    @Operation(summary = "Delete a teacher by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a teacher by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @Operation(summary = "Get all teachers")
    @GetMapping
    public ResponseEntity<List<TeacherResponseDTO>> getAll() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @Operation(summary = "Get teachers by subject")
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<TeacherResponseDTO>> getBySubject(@PathVariable String subject) {
        return ResponseEntity.ok(teacherService.getTeachersBySubject(subject));
    }
}
