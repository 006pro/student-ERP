package com.vimalesh.student_ERP.DTO.Request.Attendance;

import com.vimalesh.student_ERP.Entity.Enum.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequestDTO {
    @NotNull
    private int studentId;

    @NotNull
    private int classRoomId;

    @NotNull
    private LocalDate date;

    @NotNull
    private AttendanceStatus status;
}