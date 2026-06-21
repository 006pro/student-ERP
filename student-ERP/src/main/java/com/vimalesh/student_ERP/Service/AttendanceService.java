package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Attendance.AttendanceRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Attendance.AttendanceResponseDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {
    public void markAttendance(@Valid AttendanceRequestDTO dto) {
    }

    public void markBulkAttendance(@Valid List<AttendanceRequestDTO> dtos) {
    }

    public List<AttendanceResponseDTO> getAttendanceByStudent(int studentId) {
    }

    public List<AttendanceResponseDTO> getAttendanceByClassAndDate(int classRoomId, LocalDate date) {
    }

    public Double getAttendancePercentage(int studentId) {
    }

    public List<StudentResponseDTO> getStudentsBelowThreshold(double percent) {
    }
}
