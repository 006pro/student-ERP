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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private ClassRoomRepo classRoomRepo;
@Autowired
    AttendanceMapper attendanceMapper;
@Autowired
    AttendanceCalculator attendanceCalculator;


    public void markAttendance(@Valid AttendanceRequestDTO dto) {
        if(dto.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Attendance date cannot be in the future.");
        }
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found."));
        ClassRoom classRoom =  classRoomRepo.findById(dto.getClassRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Class room not found."));

        Attendance  attendance = attendanceMapper.toEntity(dto);
        attendance.setClassRoom(classRoom);
        attendance.setStudent(student);
        attendanceRepo.save(attendance);
    }

    public void markBulkAttendance(@Valid List<AttendanceRequestDTO> dtos) {
        dtos.forEach(this::markAttendance);
    }

    public List<AttendanceResponseDTO> getAttendanceByStudent(int studentId) {
        return attendanceRepo.findById(studentId).stream()
                .map(attendanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AttendanceResponseDTO> getAttendanceByClassAndDate(int classRoomId, LocalDate date) {
        return attendanceRepo.getAttendanceByClassAndDate(classRoomId, date)
                .stream()
                .map(attendanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Double getAttendancePercentage(int studentId) {
        int total = attendanceRepo.countByStudentId(studentId);
        int present = attendanceRepo.countByStudentIdAndStatus(studentId, AttendanceStatus.PRESENT);
        return attendanceCalculator.calculatePercentage(present,total);
    }

    public List<StudentResponseDTO> getStudentsBelowThreshold(double percent) {

        List<Student> students = studentRepo.findAll();
        return students.stream()
                .filter(s -> getAttendancePercentage(s.getId()) < percent)
                .map(s -> {
                    StudentResponseDTO dto = new StudentResponseDTO();
                    dto.setId(s.getId());
                    dto.setName(s.getName());
                    dto.setRollNo(s.getRollNo());
                    dto.setAttendancePercentage(getAttendancePercentage(s.getId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
