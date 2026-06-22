package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Attendance.AttendanceRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Attendance.AttendanceResponseDTO;
import com.vimalesh.student_ERP.Entity.Attendance;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {

    public AttendanceResponseDTO toDTO(Attendance attendance) {
        AttendanceResponseDTO dto = new AttendanceResponseDTO();
        dto.setId(attendance.getId());
        dto.setDate(attendance.getDate());
        dto.setStatus(attendance.getStatus());

        if (attendance.getStudent() != null) {
            dto.setStudentName(attendance.getStudent().getName());
            dto.setRollNo(attendance.getStudent().getRollNo());
        }

        return dto;
    }

    public Attendance toEntity(AttendanceRequestDTO dto) {
        Attendance attendance = new Attendance();
        attendance.setDate(dto.getDate());
        attendance.setStatus(dto.getStatus());
        // student and classRoom set separately in service after fetching by ID
        return attendance;
    }
}