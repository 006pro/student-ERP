package com.vimalesh.student_ERP.Controller;

import com.vimalesh.student_ERP.DTO.Request.Student.StudentRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Students", description = "CRUD and search operations for students")
@SecurityRequirement(name = "bearerAuth")
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Create a new student")
    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@Valid @RequestBody StudentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(dto));
    }

    @Operation(summary = "Update an existing student by ID")
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> update(@PathVariable int id, @Valid @RequestBody StudentRequestDTO dto) {
        return ResponseEntity.ok(studentService.updateStudent(id, dto));
    }

    @Operation(summary = "Delete a student by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a student by ID")
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @Operation(summary = "Get all students (paginated)")
    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.getAllStudents(PageRequest.of(page, size)));
    }

    @Operation(summary = "Search students by keyword")
    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(studentService.searchStudents(keyword));
    }

    @Operation(summary = "Get students by classroom ID")
    @GetMapping("/class/{classRoomId}")
    public ResponseEntity<List<StudentResponseDTO>> getByClass(@PathVariable int classRoomId) {
        return ResponseEntity.ok(studentService.getStudentsByClass(classRoomId));
    }
}
