package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Fee.FeeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Fee.FeeResponseDTO;
import com.vimalesh.student_ERP.Entity.Enum.FeeStatus;
import com.vimalesh.student_ERP.Entity.Fee;
import com.vimalesh.student_ERP.Entity.Student;
import com.vimalesh.student_ERP.Mapper.FeeMapper;
import com.vimalesh.student_ERP.Repo.FeeRepo;
import com.vimalesh.student_ERP.Repo.StudentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeeServiceTest {

    @Mock FeeRepo feeRepo;
    @Mock StudentRepo studentRepo;
    @Mock FeeMapper feeMapper;
    @InjectMocks FeeService feeService;

    private Fee sampleFee(FeeStatus status) {
        Fee fee = new Fee();
        fee.setId(1);
        fee.setAmount(5000.0);
        fee.setDueDate(LocalDate.now().plusDays(30));
        fee.setStatus(status);
        return fee;
    }

    private FeeResponseDTO sampleResponse(FeeStatus status) {
        FeeResponseDTO dto = new FeeResponseDTO();
        dto.setId(1);
        dto.setAmount(5000.0);
        dto.setStatus(status);
        return dto;
    }

    @Test
    void createFeeRecord_validDto_returnsFeeResponse() {
        FeeRequestDTO request = new FeeRequestDTO();
        request.setStudentId(1);
        request.setAmount(5000.0);
        request.setDueDate(LocalDate.now().plusDays(30));

        Student student = new Student();
        student.setId(1);
        Fee feeEntity = sampleFee(FeeStatus.PENDING);
        FeeResponseDTO response = sampleResponse(FeeStatus.PENDING);

        when(studentRepo.findById(1)).thenReturn(Optional.of(student));
        when(feeMapper.toEntity(request)).thenReturn(feeEntity);
        when(feeMapper.toDTO(feeEntity)).thenReturn(response);

        FeeResponseDTO result = feeService.createFeeRecord(request);

        assertEquals(FeeStatus.PENDING, result.getStatus());
        verify(feeRepo).save(feeEntity);
    }

    @Test
    void createFeeRecord_studentNotFound_throwsRuntimeException() {
        FeeRequestDTO request = new FeeRequestDTO();
        request.setStudentId(99);
        when(studentRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> feeService.createFeeRecord(request));
    }

    @Test
    void markAsPaid_pendingFee_setsStatusToPaid() {
        Fee fee = sampleFee(FeeStatus.PENDING);
        FeeResponseDTO response = sampleResponse(FeeStatus.PAID);

        when(feeRepo.findById(1)).thenReturn(Optional.of(fee));
        when(feeMapper.toDTO(fee)).thenReturn(response);

        FeeResponseDTO result = feeService.markAsPaid(1);

        assertEquals(FeeStatus.PAID, result.getStatus());
        verify(feeRepo).save(fee);
    }

    @Test
    void markAsPaid_alreadyPaid_throwsRuntimeException() {
        Fee fee = sampleFee(FeeStatus.PAID);
        when(feeRepo.findById(1)).thenReturn(Optional.of(fee));

        assertThrows(RuntimeException.class, () -> feeService.markAsPaid(1));
    }

    @Test
    void markAsPaid_feeNotFound_throwsRuntimeException() {
        when(feeRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> feeService.markAsPaid(99));
    }

    @Test
    void getFeesByStudent_returnsMappedList() {
        Fee fee = sampleFee(FeeStatus.PENDING);
        FeeResponseDTO response = sampleResponse(FeeStatus.PENDING);
        when(feeRepo.findByStudentId(1)).thenReturn(List.of(fee));
        when(feeMapper.toDTO(fee)).thenReturn(response);

        List<FeeResponseDTO> result = feeService.getFeesByStudent(1);

        assertEquals(1, result.size());
    }

    @Test
    void getPendingFees_returnsPendingList() {
        Fee fee = sampleFee(FeeStatus.PENDING);
        FeeResponseDTO response = sampleResponse(FeeStatus.PENDING);
        when(feeRepo.findByStatus(FeeStatus.PENDING)).thenReturn(List.of(fee));
        when(feeMapper.toDTO(fee)).thenReturn(response);

        List<FeeResponseDTO> result = feeService.getPendingFees();

        assertEquals(1, result.size());
        assertEquals(FeeStatus.PENDING, result.get(0).getStatus());
    }

    @Test
    void getOverdueFees_returnsOverdueList() {
        Fee fee = sampleFee(FeeStatus.OVERDUE);
        FeeResponseDTO response = sampleResponse(FeeStatus.OVERDUE);
        when(feeRepo.findByStatus(FeeStatus.OVERDUE)).thenReturn(List.of(fee));
        when(feeMapper.toDTO(fee)).thenReturn(response);

        List<FeeResponseDTO> result = feeService.getOverdueFees();

        assertEquals(1, result.size());
        assertEquals(FeeStatus.OVERDUE, result.get(0).getStatus());
    }
}
