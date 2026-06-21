package com.vimalesh.student_ERP.DTO.Request.Student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String rollNo;

    @Past
    private LocalDate dob;

    @NotBlank
    private String parentContact;

    private String address;

    @NotNull
    private int classRoomId;
}
