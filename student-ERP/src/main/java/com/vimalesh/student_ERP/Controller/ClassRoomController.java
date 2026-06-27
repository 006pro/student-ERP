package com.vimalesh.student_ERP.Controller;

import com.vimalesh.student_ERP.DTO.Request.Classroom.ClassRoomRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Classroom.ClassRoomResponseDTO;
import com.vimalesh.student_ERP.Service.ClassRoomService;
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
@RequestMapping("/api/classrooms")
@Tag(name = "Classrooms", description = "CRUD and teacher-assignment operations for classrooms")
@SecurityRequirement(name = "bearerAuth")
public class ClassRoomController {

    @Autowired
    ClassRoomService classRoomService;

    @Operation(summary = "Create a new classroom")
    @PostMapping
    public ResponseEntity<ClassRoomResponseDTO> create(@Valid @RequestBody ClassRoomRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(classRoomService.createClassRoom(dto));
    }

    @Operation(summary = "Update an existing classroom by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ClassRoomResponseDTO> update(@PathVariable int id, @Valid @RequestBody ClassRoomRequestDTO dto) {
        return ResponseEntity.ok(classRoomService.updateClassRoom(id, dto));
    }

    @Operation(summary = "Delete a classroom by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        classRoomService.deleteClassRoom(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a classroom by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomResponseDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(classRoomService.getClassRoomById(id));
    }

    @Operation(summary = "Get all classrooms")
    @GetMapping
    public ResponseEntity<List<ClassRoomResponseDTO>> getAll() {
        return ResponseEntity.ok(classRoomService.getAllClassRooms());
    }

    @Operation(summary = "Assign a teacher to a classroom")
    @PatchMapping("/{id}/assign-teacher/{teacherId}")
    public ResponseEntity<Void> assignTeacher(@PathVariable int id, @PathVariable int teacherId) {
        classRoomService.assignTeacher(id, teacherId);
        return ResponseEntity.noContent().build();
    }
}
