package com.vimalesh.student_ERP.DTO.Request.Attendance;

import com.vimalesh.student_ERP.Entity.Enum.AttendanceStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AttendanceRequestDTO {
    @NotNull
    private Long studentId;

    @NotNull
    private Long classRoomId;

    @NotNull
    private LocalDate date;

    @NotNull
    private AttendanceStatus status;
}