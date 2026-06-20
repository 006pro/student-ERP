package com.vimalesh.student_ERP.DTO.Response.Attendance;

import com.vimalesh.student_ERP.Entity.Enum.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponseDTO {

    private Long id;
    private String studentName;
    private String rollNo;
    private LocalDate date;
    private AttendanceStatus status;
}
