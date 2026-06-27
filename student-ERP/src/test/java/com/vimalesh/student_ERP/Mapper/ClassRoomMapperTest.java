package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Classroom.ClassRoomRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Classroom.ClassRoomResponseDTO;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Student;
import com.vimalesh.student_ERP.Entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassRoomMapperTest {

    private ClassRoomMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ClassRoomMapper();
    }

    @Test
    void toDTO_withTeacherAndStudents_mapsAllFields() {
        Teacher teacher = new Teacher();
        teacher.setName("Mr. Smith");

        Student s1 = new Student();
        Student s2 = new Student();

        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(1);
        classRoom.setName("10th Grade");
        classRoom.setSection("A");
        classRoom.setTeacher(teacher);
        classRoom.setStudents(List.of(s1, s2));

        ClassRoomResponseDTO dto = mapper.toDTO(classRoom);

        assertEquals(1, dto.getId());
        assertEquals("10th Grade", dto.getName());
        assertEquals("A", dto.getSection());
        assertEquals("Mr. Smith", dto.getTeacherName());
        assertEquals(2, dto.getTotalStudents());
    }

    @Test
    void toDTO_withoutTeacher_teacherNameIsNull() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(2);
        classRoom.setName("9th Grade");
        classRoom.setSection("B");
        classRoom.setTeacher(null);
        classRoom.setStudents(List.of());

        ClassRoomResponseDTO dto = mapper.toDTO(classRoom);

        assertNull(dto.getTeacherName());
    }

    @Test
    void toDTO_withNullStudents_totalStudentsIsZero() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(3);
        classRoom.setName("8th Grade");
        classRoom.setSection("C");
        classRoom.setStudents(null);

        ClassRoomResponseDTO dto = mapper.toDTO(classRoom);

        assertEquals(0, dto.getTotalStudents());
    }

    @Test
    void toEntity_mapsNameAndSection() {
        ClassRoomRequestDTO dto = new ClassRoomRequestDTO();
        dto.setName("7th Grade");
        dto.setSection("D");
        dto.setTeacherId(5);

        ClassRoom classRoom = mapper.toEntity(dto);

        assertEquals("7th Grade", classRoom.getName());
        assertEquals("D", classRoom.getSection());
    }
}
