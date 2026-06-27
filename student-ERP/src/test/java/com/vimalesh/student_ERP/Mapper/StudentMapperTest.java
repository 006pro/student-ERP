package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Student.StudentRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    void toDTO_withClassRoom_mapsAllFields() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setName("10th Grade");
        classRoom.setSection("A");

        Student student = new Student();
        student.setId(1);
        student.setName("Alice");
        student.setRollNo("R001");
        student.setDob(LocalDate.of(2005, 3, 10));
        student.setParentContact("9999999999");
        student.setAddress("Chennai");
        student.setPhotoUrl("photo.jpg");
        student.setClassRoom(classRoom);

        StudentResponseDTO dto = mapper.toDTO(student);

        assertEquals(1, dto.getId());
        assertEquals("Alice", dto.getName());
        assertEquals("R001", dto.getRollNo());
        assertEquals(LocalDate.of(2005, 3, 10), dto.getDob());
        assertEquals("9999999999", dto.getParentContact());
        assertEquals("Chennai", dto.getAddress());
        assertEquals("photo.jpg", dto.getPhotoUrl());
        assertEquals("10th Grade", dto.getClassName());
        assertEquals("A", dto.getSection());
    }

    @Test
    void toDTO_withoutClassRoom_classNameAndSectionAreNull() {
        Student student = new Student();
        student.setId(2);
        student.setName("Bob");

        StudentResponseDTO dto = mapper.toDTO(student);

        assertNull(dto.getClassName());
        assertNull(dto.getSection());
    }

    @Test
    void toEntity_mapsAllFields() {
        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName("Charlie");
        dto.setRollNo("R002");
        dto.setDob(LocalDate.of(2006, 5, 20));
        dto.setParentContact("8888888888");
        dto.setAddress("Coimbatore");
        dto.setClassRoomId(1);

        Student student = mapper.toEntity(dto);

        assertEquals("Charlie", student.getName());
        assertEquals("R002", student.getRollNo());
        assertEquals(LocalDate.of(2006, 5, 20), student.getDob());
        assertEquals("8888888888", student.getParentContact());
        assertEquals("Coimbatore", student.getAddress());
    }
}
