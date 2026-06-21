package com.vimalesh.student_ERP.DTO.Request.Classroom;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoomRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String section;

    private int teacherId;
}
