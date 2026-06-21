package com.vimalesh.student_ERP.Controller;


import com.vimalesh.student_ERP.DTO.Request.Attendance.AttendanceRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Attendance.AttendanceResponseDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {



    @Autowired
    AttendanceService attendanceService;


    @PostMapping
    public ResponseEntity<Void> markAttendance(@Valid @RequestBody AttendanceRequestDTO dto) {
        attendanceService.markAttendance(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<Void> markBulk(@Valid @RequestBody List<AttendanceRequestDTO> dtos) {
        attendanceService.markBulkAttendance(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getByStudent(@PathVariable int studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudent(studentId));
    }

    @GetMapping("/class/{classRoomId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getByClassAndDate(
            @PathVariable int classRoomId, @RequestParam LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByClassAndDate(classRoomId, date));
    }

    @GetMapping("/student/{studentId}/percentage")
    public ResponseEntity<Double> getPercentage(@PathVariable int studentId) {
        return ResponseEntity.ok(attendanceService.getAttendancePercentage(studentId));
    }

    @GetMapping("/below-threshold")
    public ResponseEntity<List<StudentResponseDTO>> getBelowThreshold(@RequestParam double percent) {
        return ResponseEntity.ok(attendanceService.getStudentsBelowThreshold(percent));
    }
}