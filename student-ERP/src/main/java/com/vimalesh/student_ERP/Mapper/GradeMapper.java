package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Grade.GradeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Grade.GradeResponseDTO;
import com.vimalesh.student_ERP.Entity.Grade;
import org.springframework.stereotype.Component;

@Component
public class GradeMapper {

    public GradeResponseDTO toDTO(Grade grade) {
        GradeResponseDTO dto = new GradeResponseDTO();
        dto.setId(grade.getId());
        dto.setSubject(grade.getSubject());
        dto.setMarks(grade.getMarks());
        dto.setMaxMarks(grade.getMaxMarks());
        dto.setTerm(grade.getTerm());

        if (grade.getStudent() != null) {
            dto.setStudentName(grade.getStudent().getName());
        }

        return dto;
    }

    public Grade toEntity(GradeRequestDTO dto) {
        Grade grade = new Grade();
        grade.setSubject(dto.getSubject());
        grade.setMarks(dto.getMarks());
        grade.setMaxMarks(dto.getMaxMarks());
        grade.setTerm(dto.getTerm());

        return grade;
    }
}