package com.vimalesh.student_ERP.DTO.Request.Fee;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeRequestDTO {
    @NotNull
    private int studentId;

    @Min(0)
    private double amount;

    @NotNull @Future
    private LocalDate dueDate;
}