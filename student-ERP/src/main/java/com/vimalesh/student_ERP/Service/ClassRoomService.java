package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Classroom.ClassRoomRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Classroom.ClassRoomResponseDTO;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Teacher;
import com.vimalesh.student_ERP.Mapper.ClassRoomMapper;
import com.vimalesh.student_ERP.Repo.ClassRoomRepo;
import com.vimalesh.student_ERP.Repo.TeacherRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassRoomService {

    @Autowired
    ClassRoomRepo classRoomRepo;

    @Autowired
    ClassRoomMapper classRoomMapper;

    @Autowired
    TeacherRepo teacherRepo;

    public ClassRoomResponseDTO createClassRoom(@Valid ClassRoomRequestDTO dto) {


        ClassRoom classRoom = classRoomMapper.toEntity(dto);
        if (dto.getTeacherId() != 0) {
            Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + dto.getTeacherId()));
            classRoom.setTeacher(teacher);
        }
        ClassRoom savedClassRoom = classRoomRepo.save(classRoom);

        ClassRoomResponseDTO classRoomResponseDTO = classRoomMapper.toDTO(savedClassRoom);
        return classRoomResponseDTO;

    }

    public ClassRoomResponseDTO updateClassRoom(int id, @Valid ClassRoomRequestDTO dto) {

        ClassRoom classRoom = classRoomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassRoom not found with id: " + id));

        Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + dto.getTeacherId()));
        classRoom.setTeacher(teacher);
        classRoom.setName(dto.getName());
        classRoom.setSection(dto.getSection());

        return classRoomMapper.toDTO(classRoomRepo.save(classRoom));

    }

    public void deleteClassRoom(int id) {
        if (!classRoomRepo.existsById(id)) {
            throw new RuntimeException("ClassRoom not found with id: " + id);
        }
        classRoomRepo.deleteById(id);

    }


    public ClassRoomResponseDTO getClassRoomById(int id) {

        ClassRoom classRoom = classRoomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassRoom not found with id: " + id));
        return classRoomMapper.toDTO(classRoom);
    }

    public List<ClassRoomResponseDTO> getAllClassRooms() {
        return classRoomRepo.findAll()
                .stream()
                .map(classRoomMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void assignTeacher(int id, int teacherId) {
        ClassRoom classRoom = classRoomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassRoom not found with id: " + id));
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));
        classRoom.setTeacher(teacher);
        classRoomRepo.save(classRoom);
    }

}