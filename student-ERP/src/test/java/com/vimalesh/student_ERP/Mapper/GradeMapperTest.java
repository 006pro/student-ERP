package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Grade.GradeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Grade.GradeResponseDTO;
import com.vimalesh.student_ERP.Entity.Grade;
import com.vimalesh.student_ERP.Entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeMapperTest {

    private GradeMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new GradeMapper();
    }

    @Test
    void toDTO_withStudent_mapsAllFields() {
        Student student = new Student();
        student.setName("Alice");

        Grade grade = new Grade();
        grade.setId(1);
        grade.setSubject("Mathematics");
        grade.setMarks(85.0);
        grade.setMaxMarks(100.0);
        grade.setTerm("Term1");
        grade.setStudent(student);

        GradeResponseDTO dto = mapper.toDTO(grade);

        assertEquals(1, dto.getId());
        assertEquals("Mathematics", dto.getSubject());
        assertEquals(85.0, dto.getMarks());
        assertEquals(100.0, dto.getMaxMarks());
        assertEquals("Term1", dto.getTerm());
        assertEquals("Alice", dto.getStudentName());
    }

    @Test
    void toDTO_withoutStudent_studentNameIsNull() {
        Grade grade = new Grade();
        grade.setId(2);
        grade.setSubject("Physics");
        grade.setMarks(70.0);
        grade.setMaxMarks(100.0);
        grade.setTerm("Term2");
        grade.setStudent(null);

        GradeResponseDTO dto = mapper.toDTO(grade);

        assertNull(dto.getStudentName());
        assertEquals("Physics", dto.getSubject());
    }

    @Test
    void toEntity_mapsAllFields() {
        GradeRequestDTO dto = new GradeRequestDTO();
        dto.setStudentId(1);
        dto.setSubject("Chemistry");
        dto.setMarks(92.0);
        dto.setMaxMarks(100.0);
        dto.setTerm("Term3");

        Grade grade = mapper.toEntity(dto);

        assertEquals("Chemistry", grade.getSubject());
        assertEquals(92.0, grade.getMarks());
        assertEquals(100.0, grade.getMaxMarks());
        assertEquals("Term3", grade.getTerm());
    }
}
