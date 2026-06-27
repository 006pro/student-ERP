package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Attendance.AttendanceRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Attendance.AttendanceResponseDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Entity.Attendance;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Enum.AttendanceStatus;
import com.vimalesh.student_ERP.Entity.Student;
import com.vimalesh.student_ERP.Mapper.AttendanceMapper;
import com.vimalesh.student_ERP.Repo.AttendanceRepo;
import com.vimalesh.student_ERP.Repo.ClassRoomRepo;
import com.vimalesh.student_ERP.Repo.StudentRepo;
import com.vimalesh.student_ERP.Util.AttendanceCalculator;
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
class AttendanceServiceTest {

    @Mock AttendanceRepo attendanceRepo;
    @Mock StudentRepo studentRepo;
    @Mock ClassRoomRepo classRoomRepo;
    @Mock AttendanceMapper attendanceMapper;
    @Mock AttendanceCalculator attendanceCalculator;
    @InjectMocks AttendanceService attendanceService;

    private AttendanceRequestDTO sampleRequest(LocalDate date) {
        AttendanceRequestDTO dto = new AttendanceRequestDTO();
        dto.setStudentId(1);
        dto.setClassRoomId(1);
        dto.setDate(date);
        dto.setStatus(AttendanceStatus.PRESENT);
        return dto;
    }

    @Test
    void markAttendance_validPastDate_savesAttendance() {
        AttendanceRequestDTO request = sampleRequest(LocalDate.now().minusDays(1));
        Student student = new Student();
        student.setId(1);
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(1);
        Attendance attendance = new Attendance();

        when(studentRepo.findById(1)).thenReturn(Optional.of(student));
        when(classRoomRepo.findById(1)).thenReturn(Optional.of(classRoom));
        when(attendanceMapper.toEntity(request)).thenReturn(attendance);

        attendanceService.markAttendance(request);

        verify(attendanceRepo).save(attendance);
    }

    @Test
    void markAttendance_futureDate_throwsIllegalArgumentException() {
        AttendanceRequestDTO request = sampleRequest(LocalDate.now().plusDays(1));

        assertThrows(IllegalArgumentException.class,
                () -> attendanceService.markAttendance(request));
    }

    @Test
    void markAttendance_todayDate_savesAttendance() {
        AttendanceRequestDTO request = sampleRequest(LocalDate.now());
        Student student = new Student();
        ClassRoom classRoom = new ClassRoom();
        Attendance attendance = new Attendance();

        when(studentRepo.findById(1)).thenReturn(Optional.of(student));
        when(classRoomRepo.findById(1)).thenReturn(Optional.of(classRoom));
        when(attendanceMapper.toEntity(request)).thenReturn(attendance);

        attendanceService.markAttendance(request);

        verify(attendanceRepo).save(attendance);
    }

    @Test
    void markAttendance_studentNotFound_throwsIllegalArgumentException() {
        AttendanceRequestDTO request = sampleRequest(LocalDate.now());
        when(studentRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> attendanceService.markAttendance(request));
    }

    @Test
    void markAttendance_classRoomNotFound_throwsIllegalArgumentException() {
        AttendanceRequestDTO request = sampleRequest(LocalDate.now());
        when(studentRepo.findById(1)).thenReturn(Optional.of(new Student()));
        when(classRoomRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> attendanceService.markAttendance(request));
    }

    @Test
    void markBulkAttendance_validList_savesAll() {
        AttendanceRequestDTO r1 = sampleRequest(LocalDate.now());
        AttendanceRequestDTO r2 = sampleRequest(LocalDate.now().minusDays(1));
        Student student = new Student();
        ClassRoom classRoom = new ClassRoom();
        Attendance attendance = new Attendance();

        when(studentRepo.findById(1)).thenReturn(Optional.of(student));
        when(classRoomRepo.findById(1)).thenReturn(Optional.of(classRoom));
        when(attendanceMapper.toEntity(any())).thenReturn(attendance);

        attendanceService.markBulkAttendance(List.of(r1, r2));

        verify(attendanceRepo, times(2)).save(attendance);
    }

    @Test
    void getAttendanceByClassAndDate_returnsFilteredList() {
        Attendance a = new Attendance();
        AttendanceResponseDTO dto = new AttendanceResponseDTO();
        LocalDate date = LocalDate.now();

        when(attendanceRepo.getAttendanceByClassAndDate(1, date)).thenReturn(List.of(a));
        when(attendanceMapper.toDTO(a)).thenReturn(dto);

        List<AttendanceResponseDTO> result =
                attendanceService.getAttendanceByClassAndDate(1, date);

        assertEquals(1, result.size());
    }

    @Test
    void getAttendancePercentage_returnsCalculatedValue() {
        when(attendanceRepo.countByStudentId(1)).thenReturn(10);
        when(attendanceRepo.countByStudentIdAndStatus(1, AttendanceStatus.PRESENT)).thenReturn(8);
        // AttendanceCalculator is mocked, but calculatePercentage is static - real method runs
        // We verify the repo calls are made correctly
        attendanceService.getAttendancePercentage(1);

        verify(attendanceRepo).countByStudentId(1);
        verify(attendanceRepo).countByStudentIdAndStatus(1, AttendanceStatus.PRESENT);
    }

    @Test
    void getStudentsBelowThreshold_returnsStudentsBelowPercent() {
        Student student = new Student();
        student.setId(1);
        student.setName("Alice");
        student.setRollNo("R001");

        when(studentRepo.findAll()).thenReturn(List.of(student));
        when(attendanceRepo.countByStudentId(1)).thenReturn(10);
        when(attendanceRepo.countByStudentIdAndStatus(1, AttendanceStatus.PRESENT)).thenReturn(5);
        // 50% attendance is below 75% threshold

        List<StudentResponseDTO> result = attendanceService.getStudentsBelowThreshold(75.0);

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }
}
