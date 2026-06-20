package com.vimalesh.student_ERP.DTO.Request.Fee;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class FeeRequestDTO {
    @NotNull
    private Long studentId;

    @Min(0)
    private double amount;

    @NotNull @Future
    private LocalDate dueDate;
}