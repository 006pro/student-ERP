package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Classroom.ClassRoomRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Classroom.ClassRoomResponseDTO;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import org.springframework.stereotype.Component;

@Component
public class ClassRoomMapper {

    public ClassRoomResponseDTO toDTO(ClassRoom classRoom) {
        ClassRoomResponseDTO dto = new ClassRoomResponseDTO();
        dto.setId(classRoom.getId());
        dto.setName(classRoom.getName());
        dto.setSection(classRoom.getSection());

        if (classRoom.getTeacher() != null) {
            dto.setTeacherName(classRoom.getTeacher().getName());
        }
        if (classRoom.getStudents() != null) {
            dto.setTotalStudents(classRoom.getStudents().size());
        }
        return dto;
    }

    public ClassRoom toEntity(ClassRoomRequestDTO dto) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setName(dto.getName());
        classRoom.setSection(dto.getSection());

        return classRoom;
    }
}