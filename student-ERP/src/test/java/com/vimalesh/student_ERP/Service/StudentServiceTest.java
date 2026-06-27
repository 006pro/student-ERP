package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Student.StudentRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Student;
import com.vimalesh.student_ERP.Mapper.StudentMapper;
import com.vimalesh.student_ERP.Repo.ClassRoomRepo;
import com.vimalesh.student_ERP.Repo.StudentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock StudentRepo studentRepo;
    @Mock ClassRoomRepo classRoomRepo;
    @Mock StudentMapper studentMapper;
    @InjectMocks StudentService studentService;

    private ClassRoom sampleClassRoom() {
        ClassRoom cr = new ClassRoom();
        cr.setId(1);
        cr.setName("10th Grade");
        cr.setSection("A");
        return cr;
    }

    private Student sampleStudent() {
        Student s = new Student();
        s.setId(1);
        s.setName("Alice");
        s.setRollNo("R001");
        s.setDob(LocalDate.of(2005, 3, 10));
        s.setClassRoom(sampleClassRoom());
        return s;
    }

    private StudentResponseDTO sampleResponseDTO() {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(1);
        dto.setName("Alice");
        dto.setRollNo("R001");
        dto.setClassName("10th Grade");
        dto.setSection("A");
        return dto;
    }

    private StudentRequestDTO sampleRequestDTO() {
        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName("Alice");
        dto.setRollNo("R001");
        dto.setDob(LocalDate.of(2005, 3, 10));
        dto.setParentContact("9999999999");
        dto.setClassRoomId(1);
        return dto;
    }

    @Test
    void createStudent_validDto_returnsResponseWithZeroAttendance() {
        StudentRequestDTO request = sampleRequestDTO();
        Student entity = sampleStudent();
        StudentResponseDTO response = sampleResponseDTO();

        when(classRoomRepo.findById(1)).thenReturn(Optional.of(sampleClassRoom()));
        when(studentMapper.toEntity(request)).thenReturn(entity);
        when(studentRepo.save(entity)).thenReturn(entity);
        when(studentMapper.toDTO(entity)).thenReturn(response);

        StudentResponseDTO result = studentService.createStudent(request);

        assertEquals("Alice", result.getName());
        assertEquals(0.0, result.getAttendancePercentage());
        verify(studentRepo).save(entity);
    }

    @Test
    void createStudent_classroomNotFound_throwsRuntimeException() {
        StudentRequestDTO request = sampleRequestDTO();
        when(classRoomRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.createStudent(request));
    }

    @Test
    void updateStudent_validDto_returnsUpdatedResponse() {
        StudentRequestDTO request = sampleRequestDTO();
        Student entity = sampleStudent();
        StudentResponseDTO response = sampleResponseDTO();

        when(studentRepo.findById(1)).thenReturn(Optional.of(entity));
        when(classRoomRepo.findById(1)).thenReturn(Optional.of(sampleClassRoom()));
        when(studentRepo.save(entity)).thenReturn(entity);
        when(studentMapper.toDTO(entity)).thenReturn(response);

        StudentResponseDTO result = studentService.updateStudent(1, request);

        assertEquals("Alice", result.getName());
        verify(studentRepo).save(entity);
    }

    @Test
    void updateStudent_studentNotFound_throwsRuntimeException() {
        when(studentRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> studentService.updateStudent(99, sampleRequestDTO()));
    }

    @Test
    void deleteStudent_existingId_deletesSuccessfully() {
        Student entity = sampleStudent();
        when(studentRepo.findById(1)).thenReturn(Optional.of(entity));

        studentService.deleteStudent(1);

        verify(studentRepo).delete(entity);
    }

    @Test
    void deleteStudent_studentNotFound_throwsRuntimeException() {
        when(studentRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.deleteStudent(99));
    }

    @Test
    void getStudentById_existingId_returnsStudent() {
        Student entity = sampleStudent();
        StudentResponseDTO response = sampleResponseDTO();
        when(studentRepo.findById(1)).thenReturn(Optional.of(entity));
        when(studentMapper.toDTO(entity)).thenReturn(response);

        StudentResponseDTO result = studentService.getStudentById(1);

        assertEquals(1, result.getId());
    }

    @Test
    void getStudentById_notFound_throwsRuntimeException() {
        when(studentRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.getStudentById(99));
    }

    @Test
    void getAllStudents_returnsPaginatedResult() {
        Student entity = sampleStudent();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Student> page = new PageImpl<>(List.of(entity));
        StudentResponseDTO response = sampleResponseDTO();

        when(studentRepo.findAll(pageRequest)).thenReturn(page);
        when(studentMapper.toDTO(entity)).thenReturn(response);

        Page<StudentResponseDTO> result = studentService.getAllStudents(pageRequest);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void searchStudents_byKeyword_returnsMatchingList() {
        Student entity = sampleStudent();
        StudentResponseDTO response = sampleResponseDTO();
        when(studentRepo.findByNameContaining("Alice")).thenReturn(List.of(entity));
        when(studentMapper.toDTO(entity)).thenReturn(response);

        List<StudentResponseDTO> result = studentService.searchStudents("Alice");

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void getStudentsByClass_byClassId_returnsList() {
        Student entity = sampleStudent();
        StudentResponseDTO response = sampleResponseDTO();
        when(studentRepo.findByClassRoom_Id(1)).thenReturn(List.of(entity));
        when(studentMapper.toDTO(entity)).thenReturn(response);

        List<StudentResponseDTO> result = studentService.getStudentsByClass(1);

        assertEquals(1, result.size());
    }
}
