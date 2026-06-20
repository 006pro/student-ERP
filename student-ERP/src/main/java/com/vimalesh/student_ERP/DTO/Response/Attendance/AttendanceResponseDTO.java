package com.vimalesh.student_ERP.DTO.Response.Attendance;

import com.vimalesh.student_ERP.Entity.Enum.AttendanceStatus;

import java.time.LocalDate;

public class AttendanceResponseDTO {

    private Long id;
    private String studentName;
    private String rollNo;
    private LocalDate date;
    private AttendanceStatus status;
}
