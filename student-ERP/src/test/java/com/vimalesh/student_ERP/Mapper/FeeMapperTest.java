package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Fee.FeeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Fee.FeeResponseDTO;
import com.vimalesh.student_ERP.Entity.Enum.FeeStatus;
import com.vimalesh.student_ERP.Entity.Fee;
import com.vimalesh.student_ERP.Entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FeeMapperTest {

    private FeeMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new FeeMapper();
    }

    @Test
    void toDTO_withStudent_mapsAllFields() {
        Student student = new Student();
        student.setName("Alice");

        Fee fee = new Fee();
        fee.setId(1);
        fee.setAmount(5000.0);
        fee.setDueDate(LocalDate.of(2024, 7, 31));
        fee.setPaidOn(LocalDate.of(2024, 6, 20));
        fee.setStatus(FeeStatus.PAID);
        fee.setStudent(student);

        FeeResponseDTO dto = mapper.toDTO(fee);

        assertEquals(1, dto.getId());
        assertEquals(5000.0, dto.getAmount());
        assertEquals(LocalDate.of(2024, 7, 31), dto.getDueDate());
        assertEquals(LocalDate.of(2024, 6, 20), dto.getPaidOn());
        assertEquals(FeeStatus.PAID, dto.getStatus());
        assertEquals("Alice", dto.getStudentName());
    }

    @Test
    void toDTO_withoutStudent_studentNameIsNull() {
        Fee fee = new Fee();
        fee.setId(2);
        fee.setAmount(3000.0);
        fee.setStatus(FeeStatus.PENDING);
        fee.setStudent(null);

        FeeResponseDTO dto = mapper.toDTO(fee);

        assertNull(dto.getStudentName());
        assertEquals(FeeStatus.PENDING, dto.getStatus());
    }

    @Test
    void toEntity_setsPendingStatusAndMapsAmountAndDueDate() {
        FeeRequestDTO dto = new FeeRequestDTO();
        dto.setStudentId(1);
        dto.setAmount(4500.0);
        dto.setDueDate(LocalDate.of(2024, 8, 15));

        Fee fee = mapper.toEntity(dto);

        assertEquals(4500.0, fee.getAmount());
        assertEquals(LocalDate.of(2024, 8, 15), fee.getDueDate());
        assertEquals(FeeStatus.PENDING, fee.getStatus());
    }
}
