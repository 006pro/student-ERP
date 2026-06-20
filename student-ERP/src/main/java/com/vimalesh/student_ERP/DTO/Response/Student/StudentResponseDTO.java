package com.vimalesh.student_ERP.DTO.Response.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {


    private Long id;
    private String name;
    private String rollNo;
    private LocalDate dob;
    private String parentContact;
    private String address;
    private String photoUrl;
    private String className;
    private String section;
    private double attendancePercentage;
}
