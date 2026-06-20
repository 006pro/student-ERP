package com.vimalesh.student_ERP.DTO.Request.Teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class TeacherRequestDTO {
    @NotBlank
    private String name;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String subject;
}
