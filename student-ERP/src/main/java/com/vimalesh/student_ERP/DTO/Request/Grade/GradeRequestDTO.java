package com.vimalesh.student_ERP.DTO.Request.Grade;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GradeRequestDTO {

    @NotNull
    private Long studentId;

    @NotBlank
    private String subject;

    @Min(0)
    private double marks;

    @Min(1)
    private double maxMarks;

    @NotBlank
    private String term;}
