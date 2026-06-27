package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Teacher.TeacherRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Teacher.TeacherResponseDTO;
import com.vimalesh.student_ERP.Entity.Teacher;
import com.vimalesh.student_ERP.Mapper.TeacherMapper;
import com.vimalesh.student_ERP.Repo.ClassRoomRepo;
import com.vimalesh.student_ERP.Repo.TeacherRepo;
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
class TeacherServiceTest {

    @Mock TeacherRepo teacherRepo;
    @Mock ClassRoomRepo classRoomRepo;
    @Mock TeacherMapper teacherMapper;
    @InjectMocks TeacherService teacherService;

    private Teacher sampleTeacher() {
        Teacher t = new Teacher();
        t.setId(1);
        t.setName("Mr. Smith");
        t.setEmail("smith@school.com");
        t.setPhone("9876543210");
        t.setSubject("Mathematics");
        return t;
    }

    private TeacherRequestDTO sampleRequest() {
        TeacherRequestDTO dto = new TeacherRequestDTO();
        dto.setName("Mr. Smith");
        dto.setEmail("smith@school.com");
        dto.setPhone("9876543210");
        dto.setSubject("Mathematics");
        return dto;
    }

    private TeacherResponseDTO sampleResponse() {
        TeacherResponseDTO dto = new TeacherResponseDTO();
        dto.setId(1);
        dto.setName("Mr. Smith");
        dto.setEmail("smith@school.com");
        dto.setSubject("Mathematics");
        return dto;
    }

    @Test
    void createTeacher_validDto_returnsResponse() {
        Teacher entity = sampleTeacher();
        TeacherResponseDTO response = sampleResponse();
        when(teacherMapper.toEntity(any())).thenReturn(entity);
        when(teacherMapper.toDTO(entity)).thenReturn(response);

        TeacherResponseDTO result = teacherService.createTeacher(sampleRequest());

        assertEquals("Mr. Smith", result.getName());
        verify(teacherRepo).save(entity);
    }

    @Test
    void updateTeacher_validId_returnsUpdatedResponse() {
        Teacher entity = sampleTeacher();
        TeacherRequestDTO request = sampleRequest();
        request.setName("Mr. Updated");

        TeacherResponseDTO updatedResponse = sampleResponse();
        updatedResponse.setName("Mr. Updated");

        when(teacherRepo.findById(1)).thenReturn(Optional.of(entity));
        when(teacherMapper.toDTO(entity)).thenReturn(updatedResponse);

        TeacherResponseDTO result = teacherService.updateTeacher(1, request);

        assertEquals("Mr. Updated", result.getName());
    }

    @Test
    void updateTeacher_notFound_throwsRuntimeException() {
        when(teacherRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> teacherService.updateTeacher(99, sampleRequest()));
    }

    @Test
    void deleteTeacher_existingId_deletesSuccessfully() {
        when(teacherRepo.existsById(1)).thenReturn(true);

        teacherService.deleteTeacher(1);

        verify(teacherRepo).deleteById(1);
    }

    @Test
    void deleteTeacher_notFound_throwsRuntimeException() {
        when(teacherRepo.existsById(99)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> teacherService.deleteTeacher(99));
    }

    @Test
    void getTeacherById_existingId_returnsTeacher() {
        Teacher entity = sampleTeacher();
        TeacherResponseDTO response = sampleResponse();
        when(teacherRepo.existsById(1)).thenReturn(true);
        when(teacherRepo.findById(1)).thenReturn(Optional.of(entity));
        when(teacherMapper.toDTO(entity)).thenReturn(response);

        TeacherResponseDTO result = teacherService.getTeacherById(1);

        assertEquals(1, result.getId());
    }

    @Test
    void getTeacherById_notFound_throwsRuntimeException() {
        when(teacherRepo.existsById(99)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> teacherService.getTeacherById(99));
    }

    @Test
    void getAllTeachers_returnsMappedList() {
        Teacher entity = sampleTeacher();
        TeacherResponseDTO response = sampleResponse();
        when(teacherRepo.findAll()).thenReturn(List.of(entity));
        when(teacherMapper.toDTO(entity)).thenReturn(response);

        List<TeacherResponseDTO> result = teacherService.getAllTeachers();

        assertEquals(1, result.size());
        assertEquals("Mr. Smith", result.get(0).getName());
    }

    @Test
    void getTeachersBySubject_returnsMatchingList() {
        Teacher entity = sampleTeacher();
        TeacherResponseDTO response = sampleResponse();
        when(teacherRepo.findBySubject("Mathematics")).thenReturn(List.of(entity));
        when(teacherMapper.toDTO(entity)).thenReturn(response);

        List<TeacherResponseDTO> result = teacherService.getTeachersBySubject("Mathematics");

        assertEquals(1, result.size());
    }
}
