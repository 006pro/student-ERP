package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Teacher.TeacherRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Teacher.TeacherResponseDTO;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Teacher;
import com.vimalesh.student_ERP.Mapper.TeacherMapper;
import com.vimalesh.student_ERP.Repo.ClassRoomRepo;
import com.vimalesh.student_ERP.Repo.TeacherRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeacherService {

    @Autowired
    TeacherRepo teacherRepo;

    @Autowired
    ClassRoomRepo classRoomRepo;

    @Autowired
    TeacherMapper teacherMapper;

    public TeacherResponseDTO createTeacher(@Valid TeacherRequestDTO dto) {

        Teacher teacher = teacherMapper.toEntity(dto);

        teacherRepo.save(teacher);
        return teacherMapper.toDTO(teacher);
    }

    public TeacherResponseDTO updateTeacher(int id, @Valid TeacherRequestDTO dto) {
        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));

        teacher.setName(dto.getName());
        teacher.setEmail(dto.getEmail());
        teacher.setPhone(dto.getPhone());
        teacher.setSubject(dto.getSubject());
        return teacherMapper.toDTO(teacher);
    }

    public void deleteTeacher(int id) {
        if(!teacherRepo.existsById(id)) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        teacherRepo.deleteById(id);
    }

    public TeacherResponseDTO getTeacherById(int id) {
        if(!teacherRepo.existsById(id)) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        return teacherMapper.toDTO(teacherRepo.findById(id).get());
    }

    public List<TeacherResponseDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepo.findAll();
        return teachers.stream()
                .map(teacherMapper::toDTO)
                .toList();
    }

    public List<TeacherResponseDTO> getTeachersBySubject(String subject) {
        List<Teacher> teachers = teacherRepo.findBySubject(subject);

        return teachers.stream()
                .map(teacherMapper::toDTO)
                .toList();
    }
}
