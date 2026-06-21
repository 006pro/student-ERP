package com.vimalesh.student_ERP.DTO.Response.Classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoomResponseDTO {
    private int id;
    private String name;
    private String section;
    private String teacherName;     // flattened
    private int totalStudents;

}
