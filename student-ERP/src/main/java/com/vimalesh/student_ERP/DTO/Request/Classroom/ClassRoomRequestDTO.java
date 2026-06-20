package com.vimalesh.student_ERP.DTO.Request.Classroom;

import jakarta.validation.constraints.NotBlank;

public class ClassRoomRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String section;

    private Long teacherId;
}
