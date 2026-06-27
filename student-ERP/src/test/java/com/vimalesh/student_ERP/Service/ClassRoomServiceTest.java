package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Classroom.ClassRoomRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Classroom.ClassRoomResponseDTO;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Teacher;
import com.vimalesh.student_ERP.Mapper.ClassRoomMapper;
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
class ClassRoomServiceTest {

    @Mock ClassRoomRepo classRoomRepo;
    @Mock TeacherRepo teacherRepo;
    @Mock ClassRoomMapper classRoomMapper;
    @InjectMocks ClassRoomService classRoomService;

    private ClassRoom sampleClassRoom() {
        ClassRoom cr = new ClassRoom();
        cr.setId(1);
        cr.setName("10th Grade");
        cr.setSection("A");
        return cr;
    }

    private Teacher sampleTeacher() {
        Teacher t = new Teacher();
        t.setId(1);
        t.setName("Mr. Smith");
        return t;
    }

    private ClassRoomRequestDTO sampleRequest(boolean withTeacher) {
        ClassRoomRequestDTO dto = new ClassRoomRequestDTO();
        dto.setName("10th Grade");
        dto.setSection("A");
        dto.setTeacherId(withTeacher ? 1 : 0);
        return dto;
    }

    private ClassRoomResponseDTO sampleResponse() {
        ClassRoomResponseDTO dto = new ClassRoomResponseDTO();
        dto.setId(1);
        dto.setName("10th Grade");
        dto.setSection("A");
        dto.setTeacherName("Mr. Smith");
        return dto;
    }

    @Test
    void createClassRoom_withTeacher_assignsTeacherAndReturnsResponse() {
        ClassRoom entity = sampleClassRoom();
        Teacher teacher = sampleTeacher();
        ClassRoomResponseDTO response = sampleResponse();

        when(classRoomMapper.toEntity(any())).thenReturn(entity);
        when(teacherRepo.findById(1)).thenReturn(Optional.of(teacher));
        when(classRoomRepo.save(entity)).thenReturn(entity);
        when(classRoomMapper.toDTO(entity)).thenReturn(response);

        ClassRoomResponseDTO result = classRoomService.createClassRoom(sampleRequest(true));

        assertEquals("10th Grade", result.getName());
        verify(classRoomRepo).save(entity);
    }

    @Test
    void createClassRoom_withoutTeacher_skipsTeacherAssignment() {
        ClassRoom entity = sampleClassRoom();
        ClassRoomResponseDTO response = sampleResponse();

        when(classRoomMapper.toEntity(any())).thenReturn(entity);
        when(classRoomRepo.save(entity)).thenReturn(entity);
        when(classRoomMapper.toDTO(entity)).thenReturn(response);

        classRoomService.createClassRoom(sampleRequest(false));

        verify(teacherRepo, never()).findById(anyInt());
    }

    @Test
    void updateClassRoom_validIds_returnsUpdatedResponse() {
        ClassRoom entity = sampleClassRoom();
        Teacher teacher = sampleTeacher();
        ClassRoomResponseDTO response = sampleResponse();

        when(classRoomRepo.findById(1)).thenReturn(Optional.of(entity));
        when(teacherRepo.findById(1)).thenReturn(Optional.of(teacher));
        when(classRoomRepo.save(entity)).thenReturn(entity);
        when(classRoomMapper.toDTO(entity)).thenReturn(response);

        ClassRoomResponseDTO result = classRoomService.updateClassRoom(1, sampleRequest(true));

        assertEquals("10th Grade", result.getName());
    }

    @Test
    void updateClassRoom_classRoomNotFound_throwsRuntimeException() {
        when(classRoomRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> classRoomService.updateClassRoom(99, sampleRequest(true)));
    }

    @Test
    void deleteClassRoom_existingId_deletesSuccessfully() {
        when(classRoomRepo.existsById(1)).thenReturn(true);

        classRoomService.deleteClassRoom(1);

        verify(classRoomRepo).deleteById(1);
    }

    @Test
    void deleteClassRoom_notFound_throwsRuntimeException() {
        when(classRoomRepo.existsById(99)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> classRoomService.deleteClassRoom(99));
    }

    @Test
    void getClassRoomById_existingId_returnsClassRoom() {
        ClassRoom entity = sampleClassRoom();
        ClassRoomResponseDTO response = sampleResponse();
        when(classRoomRepo.findById(1)).thenReturn(Optional.of(entity));
        when(classRoomMapper.toDTO(entity)).thenReturn(response);

        ClassRoomResponseDTO result = classRoomService.getClassRoomById(1);

        assertEquals(1, result.getId());
    }

    @Test
    void getClassRoomById_notFound_throwsRuntimeException() {
        when(classRoomRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> classRoomService.getClassRoomById(99));
    }

    @Test
    void getAllClassRooms_returnsMappedList() {
        ClassRoom entity = sampleClassRoom();
        ClassRoomResponseDTO response = sampleResponse();
        when(classRoomRepo.findAll()).thenReturn(List.of(entity));
        when(classRoomMapper.toDTO(entity)).thenReturn(response);

        List<ClassRoomResponseDTO> result = classRoomService.getAllClassRooms();

        assertEquals(1, result.size());
    }

    @Test
    void assignTeacher_validIds_assignsAndSaves() {
        ClassRoom entity = sampleClassRoom();
        Teacher teacher = sampleTeacher();
        when(classRoomRepo.findById(1)).thenReturn(Optional.of(entity));
        when(teacherRepo.findById(1)).thenReturn(Optional.of(teacher));

        classRoomService.assignTeacher(1, 1);

        assertEquals(teacher, entity.getTeacher());
        verify(classRoomRepo).save(entity);
    }

    @Test
    void assignTeacher_classRoomNotFound_throwsRuntimeException() {
        when(classRoomRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> classRoomService.assignTeacher(99, 1));
    }
}
