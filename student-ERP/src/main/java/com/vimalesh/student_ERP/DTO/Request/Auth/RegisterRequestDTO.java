package com.vimalesh.student_ERP.DTO.Request.Auth;

import com.vimalesh.student_ERP.Entity.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank @Size(min = 6)
    private String password;

    @NotNull
    private Role role;
}
