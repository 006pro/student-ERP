package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Grade.GradeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Grade.GradeResponseDTO;
import com.vimalesh.student_ERP.Entity.Grade;
import com.vimalesh.student_ERP.Entity.Student;
import com.vimalesh.student_ERP.Mapper.GradeMapper;
import com.vimalesh.student_ERP.Repo.GradeRepo;
import com.vimalesh.student_ERP.Repo.StudentRepo;
import com.vimalesh.student_ERP.Util.GradeCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeServiceTest {

    @Mock GradeRepo gradeRepository;
    @Mock StudentRepo studentRepo;
    @Mock GradeMapper gradeMapper;
    @Mock GradeCalculator gradeCalculator;
    @InjectMocks GradeService gradeService;

    private GradeRequestDTO sampleRequest() {
        GradeRequestDTO dto = new GradeRequestDTO();
        dto.setStudentId(1);
        dto.setSubject("Mathematics");
        dto.setMarks(85.0);
        dto.setMaxMarks(100.0);
        dto.setTerm("Term1");
        return dto;
    }

    private Grade sampleGrade() {
        Grade g = new Grade();
        g.setId(1);
        g.setSubject("Mathematics");
        g.setMarks(85.0);
        g.setMaxMarks(100.0);
        g.setTerm("Term1");
        return g;
    }

    private GradeResponseDTO sampleResponse() {
        GradeResponseDTO dto = new GradeResponseDTO();
        dto.setId(1);
        dto.setSubject("Mathematics");
        dto.setMarks(85.0);
        dto.setMaxMarks(100.0);
        dto.setTerm("Term1");
        dto.setGrade("A");
        return dto;
    }

    @Test
    void saveGrade_validDto_returnsResponseWithGrade() {
        Student student = new Student();
        student.setId(1);
        Grade entity = sampleGrade();
        GradeResponseDTO response = sampleResponse();

        when(studentRepo.findById(1)).thenReturn(Optional.of(student));
        when(gradeMapper.toEntity(any())).thenReturn(entity);
        when(gradeRepository.save(entity)).thenReturn(entity);
        when(gradeMapper.toDTO(entity)).thenReturn(response);
        when(gradeCalculator.calculateGrade(85.0, 100.0)).thenReturn("A");

        GradeResponseDTO result = gradeService.saveGrade(sampleRequest());

        assertEquals("A", result.getGrade());
        verify(gradeRepository).save(entity);
    }

    @Test
    void saveGrade_studentNotFound_throwsRuntimeException() {
        when(studentRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gradeService.saveGrade(sampleRequest()));
    }

    @Test
    void saveBulkGrades_savesEachGrade() {
        Student student = new Student();
        Grade entity = sampleGrade();
        GradeResponseDTO response = sampleResponse();

        when(studentRepo.findById(1)).thenReturn(Optional.of(student));
        when(gradeMapper.toEntity(any())).thenReturn(entity);
        when(gradeRepository.save(entity)).thenReturn(entity);
        when(gradeMapper.toDTO(entity)).thenReturn(response);
        when(gradeCalculator.calculateGrade(anyDouble(), anyDouble())).thenReturn("A");

        gradeService.saveBulkGrades(List.of(sampleRequest(), sampleRequest()));

        verify(gradeRepository, times(2)).save(entity);
    }

    @Test
    void getGradesByStudent_noGrades_returnsEmptyList() {
        when(gradeRepository.findAllByStudentId(1)).thenReturn(List.of());

        List<GradeResponseDTO> result = gradeService.getGradesByStudent(1);

        assertTrue(result.isEmpty());
    }

    @Test
    void getGradesByStudentAndTerm_noGrades_returnsEmptyList() {
        when(gradeRepository.findAllByStudentIdAndTerm(1, "Term1")).thenReturn(List.of());

        List<GradeResponseDTO> result = gradeService.getGradesByStudentAndTerm(1, "Term1");

        assertTrue(result.isEmpty());
    }

    @Test
    void calculateAverage_noGrades_returnsZero() {
        when(gradeRepository.findAllByStudentIdAndTerm(1, "Term1")).thenReturn(List.of());

        Double result = gradeService.calculateAverage(1, "Term1");

        assertEquals(0.0, result);
    }

    @Test
    void calculateAverage_withGrades_returnsCorrectAverage() {
        Grade g1 = new Grade();
        g1.setMarks(80.0);
        g1.setMaxMarks(100.0);

        Grade g2 = new Grade();
        g2.setMarks(90.0);
        g2.setMaxMarks(100.0);

        when(gradeRepository.findAllByStudentIdAndTerm(1, "Term1")).thenReturn(List.of(g1, g2));
        // Real GradeCalculator.calculatePercentage runs: 80% and 90%, average = 85%
        // But gradeCalculator is mocked so we need to stub it
        when(gradeCalculator.calculatePercentage(80.0, 100.0)).thenReturn(80.0);
        when(gradeCalculator.calculatePercentage(90.0, 100.0)).thenReturn(90.0);

        Double result = gradeService.calculateAverage(1, "Term1");

        assertEquals(85.0, result);
    }
}
