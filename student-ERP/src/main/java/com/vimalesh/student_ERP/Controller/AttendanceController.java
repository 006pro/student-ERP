package com.vimalesh.student_ERP.Controller;

import com.vimalesh.student_ERP.DTO.Request.Attendance.AttendanceRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Attendance.AttendanceResponseDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@Tag(name = "Attendance", description = "Mark and query student attendance records")
@SecurityRequirement(name = "bearerAuth")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @Operation(summary = "Mark attendance for a single student")
    @PostMapping
    public ResponseEntity<Void> markAttendance(@Valid @RequestBody AttendanceRequestDTO dto) {
        attendanceService.markAttendance(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Mark attendance in bulk for multiple students")
    @PostMapping("/bulk")
    public ResponseEntity<Void> markBulk(@Valid @RequestBody List<AttendanceRequestDTO> dtos) {
        attendanceService.markBulkAttendance(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all attendance records for a student")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getByStudent(@PathVariable int studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudent(studentId));
    }

    @Operation(summary = "Get attendance for a classroom on a specific date")
    @GetMapping("/class/{classRoomId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getByClassAndDate(
            @PathVariable int classRoomId, @RequestParam LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByClassAndDate(classRoomId, date));
    }

    @Operation(summary = "Get attendance percentage for a student")
    @GetMapping("/student/{studentId}/percentage")
    public ResponseEntity<Double> getPercentage(@PathVariable int studentId) {
        return ResponseEntity.ok(attendanceService.getAttendancePercentage(studentId));
    }

    @Operation(summary = "Get students with attendance below a threshold percentage")
    @GetMapping("/below-threshold")
    public ResponseEntity<List<StudentResponseDTO>> getBelowThreshold(@RequestParam double percent) {
        return ResponseEntity.ok(attendanceService.getStudentsBelowThreshold(percent));
    }
}
