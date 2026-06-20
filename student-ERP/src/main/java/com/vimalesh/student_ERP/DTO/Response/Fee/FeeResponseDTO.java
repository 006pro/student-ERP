package com.vimalesh.student_ERP.DTO.Response.Fee;

import com.vimalesh.student_ERP.Entity.Enum.FeeStatus;

import java.time.LocalDate;

public class FeeResponseDTO {
    private Long id;
    private String studentName;
    private double amount;
    private LocalDate dueDate;
    private LocalDate paidOn;
    private FeeStatus status;
}
