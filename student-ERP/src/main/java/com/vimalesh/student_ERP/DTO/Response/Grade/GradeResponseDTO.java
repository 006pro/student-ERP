package com.vimalesh.student_ERP.DTO.Response.Grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeResponseDTO {

    private int id;
    private String studentName;
    private String subject;
    private double marks;
    private double maxMarks;
    private String grade;
    private String term;
}
