package com.vimalesh.student_ERP.DTO.Response.Fee;

import com.vimalesh.student_ERP.Entity.Enum.FeeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeResponseDTO {
    private int id;
    private String studentName;
    private double amount;
    private LocalDate dueDate;
    private LocalDate paidOn;
    private FeeStatus status;
}
