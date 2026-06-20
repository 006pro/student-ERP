package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Teacher.TeacherRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Teacher.TeacherResponseDTO;
import com.vimalesh.student_ERP.Entity.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


    @Component
    public class TeacherMapper {

        public TeacherResponseDTO toDTO(Teacher teacher) {
            TeacherResponseDTO dto = new TeacherResponseDTO();
            dto.setId(teacher.getId());
            dto.setName(teacher.getName());
            dto.setEmail(teacher.getEmail());
            dto.setPhone(teacher.getPhone());
            dto.setSubject(teacher.getSubject());

            if (teacher.getClassRooms() != null) {
                List<String> classNames = teacher.getClassRooms().stream()
                        .map(c -> c.getName() + "-" + c.getSection())
                        .collect(Collectors.toList());
                dto.setClassesHandled(classNames);
            }
            return dto;
        }

        public Teacher toEntity(TeacherRequestDTO dto) {
            Teacher teacher = new Teacher();
            teacher.setName(dto.getName());
            teacher.setEmail(dto.getEmail());
            teacher.setPhone(dto.getPhone());
            teacher.setSubject(dto.getSubject());
            return teacher;
        }
    }

