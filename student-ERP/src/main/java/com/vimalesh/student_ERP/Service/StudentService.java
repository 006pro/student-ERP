package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Student.StudentRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Repo.ClassRoomRepo;
import com.vimalesh.student_ERP.Repo.StudentRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class StudentService {
    @Autowired
    ClassRoomRepo classRoomRepo;
    @Autowired
    StudentRepo studentRepo;

    public StudentResponseDTO createStudent(@Valid StudentRequestDTO dto) {
      ClassRoom classroom = classRoomRepo.findById(dto.getClassRoomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found with id: " + dto.getClassRoomId()));

        StudentResponseDTO responseDTO = new StudentResponseDTO();
        return null;
    }

    public StudentResponseDTO updateStudent(Long id, @Valid StudentRequestDTO dto) {
        return null;
    }

    public void deleteStudent(Long id) {
    }

    public StudentResponseDTO getStudentById(Long id) {
        return null;
    }

    public Page<StudentResponseDTO> getAllStudents(PageRequest of) {
        return null;
    }

    public List<StudentResponseDTO> searchStudents(String keyword) {
        return List.of();
    }

    public List<StudentResponseDTO> getStudentsByClass(Long classRoomId) {
        return List.of();
    }
}
