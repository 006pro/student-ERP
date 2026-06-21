package com.vimalesh.student_ERP.Controller;

import com.vimalesh.student_ERP.DTO.Request.Classroom.ClassRoomRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Classroom.ClassRoomResponseDTO;
import com.vimalesh.student_ERP.Service.ClassRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class ClassRoomController {


    @Autowired
    ClassRoomService classRoomService;



    @PostMapping
    public ResponseEntity<ClassRoomResponseDTO> create(@Valid @RequestBody ClassRoomRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(classRoomService.createClassRoom(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassRoomResponseDTO> update(@PathVariable int id, @Valid @RequestBody ClassRoomRequestDTO dto) {
        return ResponseEntity.ok(classRoomService.updateClassRoom(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        classRoomService.deleteClassRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomResponseDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(classRoomService.getClassRoomById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClassRoomResponseDTO>> getAll() {
        return ResponseEntity.ok(classRoomService.getAllClassRooms());
    }

    @PatchMapping("/{id}/assign-teacher/{teacherId}")
    public ResponseEntity<Void> assignTeacher(@PathVariable int id, @PathVariable int teacherId) {
        classRoomService.assignTeacher(id, teacherId);
        return ResponseEntity.noContent().build();
    }
}