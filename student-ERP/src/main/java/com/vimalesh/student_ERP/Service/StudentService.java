package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Student.StudentRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Student;
import com.vimalesh.student_ERP.Mapper.StudentMapper;
import com.vimalesh.student_ERP.Repo.ClassRoomRepo;
import com.vimalesh.student_ERP.Repo.StudentRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class StudentService {
    @Autowired
    ClassRoomRepo classRoomRepo;
    @Autowired
    StudentRepo studentRepo;

    @Autowired
    StudentMapper studentMapper;

    public StudentResponseDTO createStudent(@Valid StudentRequestDTO dto) {
        ClassRoom classroom = classRoomRepo.findById(dto.getClassRoomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found with id: " + dto.getClassRoomId()));

        Student student = studentMapper.toEntity(dto);
        student.setClassRoom(classroom);

        Student saved = studentRepo.save(student);
        StudentResponseDTO responseDTO = studentMapper.toDTO(saved);
        responseDTO.setAttendancePercentage(0.0);

        return responseDTO;
    }

    public StudentResponseDTO updateStudent(int id, @Valid StudentRequestDTO dto) {


        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        ClassRoom classRoom = classRoomRepo.findById(dto.getClassRoomId())
                .orElseThrow(() -> new RuntimeException("ClassRoom not found with id: " + dto.getClassRoomId()));

        student.setName(dto.getName());
        student.setRollNo(dto.getRollNo());
        student.setDob(dto.getDob());
        student.setParentContact(dto.getParentContact());
        student.setAddress(dto.getAddress());
        student.setClassRoom(classRoom);

        Student updated = studentRepo.save(student);
        return studentMapper.toDTO(updated);


    }

    public void deleteStudent(int id) {

        Student student = studentRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
            studentRepo.delete(student);
    }

    public StudentResponseDTO getStudentById(int id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return studentMapper.toDTO(student);
    }

    public Page<StudentResponseDTO> getAllStudents(PageRequest of) {
        Page<Student> students = studentRepo.findAll(of);
        return students.map(studentMapper::toDTO);
    }

    public List<StudentResponseDTO> searchStudents(String keyword) {

        return studentRepo.findByNameContaining(keyword)
                .stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<StudentResponseDTO> getStudentsByClass(int classRoomId) {
        return studentRepo.findByClassRoom_Id(classRoomId)
                .stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
