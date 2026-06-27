package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Attendance.AttendanceRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Attendance.AttendanceResponseDTO;
import com.vimalesh.student_ERP.Entity.Attendance;
import com.vimalesh.student_ERP.Entity.Enum.AttendanceStatus;
import com.vimalesh.student_ERP.Entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceMapperTest {

    private AttendanceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AttendanceMapper();
    }

    @Test
    void toDTO_withStudent_mapsAllFields() {
        Student student = new Student();
        student.setName("Alice");
        student.setRollNo("R001");

        Attendance attendance = new Attendance();
        attendance.setId(1);
        attendance.setDate(LocalDate.of(2024, 6, 15));
        attendance.setStatus(AttendanceStatus.PRESENT);
        attendance.setStudent(student);

        AttendanceResponseDTO dto = mapper.toDTO(attendance);

        assertEquals(1, dto.getId());
        assertEquals(LocalDate.of(2024, 6, 15), dto.getDate());
        assertEquals(AttendanceStatus.PRESENT, dto.getStatus());
        assertEquals("Alice", dto.getStudentName());
        assertEquals("R001", dto.getRollNo());
    }

    @Test
    void toDTO_withoutStudent_studentNameAndRollNoAreNull() {
        Attendance attendance = new Attendance();
        attendance.setId(2);
        attendance.setDate(LocalDate.now());
        attendance.setStatus(AttendanceStatus.ABSENT);
        attendance.setStudent(null);

        AttendanceResponseDTO dto = mapper.toDTO(attendance);

        assertNull(dto.getStudentName());
        assertNull(dto.getRollNo());
    }

    @Test
    void toEntity_mapsDateAndStatus() {
        AttendanceRequestDTO dto = new AttendanceRequestDTO();
        dto.setDate(LocalDate.of(2024, 6, 20));
        dto.setStatus(AttendanceStatus.LATE);
        dto.setStudentId(1);
        dto.setClassRoomId(2);

        Attendance attendance = mapper.toEntity(dto);

        assertEquals(LocalDate.of(2024, 6, 20), attendance.getDate());
        assertEquals(AttendanceStatus.LATE, attendance.getStatus());
    }
}
