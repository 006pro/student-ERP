package com.vimalesh.student_ERP.DTO.Request.Teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
