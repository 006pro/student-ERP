package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Student.StudentRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentResponseDTO toDTO(Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setRollNo(student.getRollNo());
        dto.setDob(student.getDob());
        dto.setParentContact(student.getParentContact());
        dto.setAddress(student.getAddress());
        dto.setPhotoUrl(student.getPhotoUrl());

        if (student.getClassRoom() != null) {
            dto.setClassName(student.getClassRoom().getName());
            dto.setSection(student.getClassRoom().getSection());
        }

        return dto;
    }

    public Student toEntity(StudentRequestDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setRollNo(dto.getRollNo());
        student.setDob(dto.getDob());
        student.setParentContact(dto.getParentContact());
        student.setAddress(dto.getAddress());

        return student;
    }
}