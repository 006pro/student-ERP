package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Teacher.TeacherRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Teacher.TeacherResponseDTO;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherMapperTest {

    private TeacherMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TeacherMapper();
    }

    @Test
    void toDTO_withClassRooms_mapsAllFields() {
        ClassRoom c1 = new ClassRoom();
        c1.setName("10th Grade");
        c1.setSection("A");

        ClassRoom c2 = new ClassRoom();
        c2.setName("11th Grade");
        c2.setSection("B");

        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("Mr. Smith");
        teacher.setEmail("smith@school.com");
        teacher.setPhone("9876543210");
        teacher.setSubject("Mathematics");
        teacher.setClassRooms(List.of(c1, c2));

        TeacherResponseDTO dto = mapper.toDTO(teacher);

        assertEquals(1, dto.getId());
        assertEquals("Mr. Smith", dto.getName());
        assertEquals("smith@school.com", dto.getEmail());
        assertEquals("9876543210", dto.getPhone());
        assertEquals("Mathematics", dto.getSubject());
        assertEquals(2, dto.getClassesHandled().size());
        assertTrue(dto.getClassesHandled().contains("10th Grade-A"));
        assertTrue(dto.getClassesHandled().contains("11th Grade-B"));
    }

    @Test
    void toDTO_withNullClassRooms_classesHandledIsNull() {
        Teacher teacher = new Teacher();
        teacher.setId(2);
        teacher.setName("Ms. Jane");
        teacher.setClassRooms(null);

        TeacherResponseDTO dto = mapper.toDTO(teacher);

        assertNull(dto.getClassesHandled());
    }

    @Test
    void toEntity_mapsAllFields() {
        TeacherRequestDTO dto = new TeacherRequestDTO();
        dto.setName("Mr. Brown");
        dto.setEmail("brown@school.com");
        dto.setPhone("9123456789");
        dto.setSubject("Physics");

        Teacher teacher = mapper.toEntity(dto);

        assertEquals("Mr. Brown", teacher.getName());
        assertEquals("brown@school.com", teacher.getEmail());
        assertEquals("9123456789", teacher.getPhone());
        assertEquals("Physics", teacher.getSubject());
    }
}
