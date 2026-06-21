package com.vimalesh.student_ERP.DTO.Response.Teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String subject;
    private List<String> classesHandled;
}
