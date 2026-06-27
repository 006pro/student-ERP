package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Attendance;
import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Enum.AttendanceStatus;
import com.vimalesh.student_ERP.Entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AttendanceRepoTest {

    @Autowired AttendanceRepo attendanceRepo;
    @Autowired StudentRepo studentRepo;
    @Autowired ClassRoomRepo classRoomRepo;

    private Student student;
    private ClassRoom classRoom;
    private LocalDate today;

    @BeforeEach
    void setUp() {
        today = LocalDate.now();

        classRoom = new ClassRoom();
        classRoom.setName("10th Grade");
        classRoom.setSection("A");
        classRoom = classRoomRepo.save(classRoom);

        student = new Student();
        student.setName("Alice");
        student.setRollNo("R001");
        student.setParentContact("9999999999");
        student.setClassRoom(classRoom);
        student = studentRepo.save(student);

        Attendance a1 = new Attendance();
        a1.setDate(today);
        a1.setStatus(AttendanceStatus.PRESENT);
        a1.setStudent(student);
        a1.setClassRoom(classRoom);
        attendanceRepo.save(a1);

        Attendance a2 = new Attendance();
        a2.setDate(today.minusDays(1));
        a2.setStatus(AttendanceStatus.ABSENT);
        a2.setStudent(student);
        a2.setClassRoom(classRoom);
        attendanceRepo.save(a2);

        Attendance a3 = new Attendance();
        a3.setDate(today.minusDays(2));
        a3.setStatus(AttendanceStatus.PRESENT);
        a3.setStudent(student);
        a3.setClassRoom(classRoom);
        attendanceRepo.save(a3);
    }

    @Test
    void getAttendanceByClassAndDate_returnsMatchingRecords() {
        List<Attendance> result = attendanceRepo.getAttendanceByClassAndDate(classRoom.getId(), today);
        assertEquals(1, result.size());
        assertEquals(AttendanceStatus.PRESENT, result.get(0).getStatus());
    }

    @Test
    void getAttendanceByClassAndDate_nonMatchingDate_returnsEmpty() {
        List<Attendance> result = attendanceRepo.getAttendanceByClassAndDate(
                classRoom.getId(), today.plusDays(10));
        assertTrue(result.isEmpty());
    }

    @Test
    void countByStudentId_returnsCorrectCount() {
        int count = attendanceRepo.countByStudentId(student.getId());
        assertEquals(3, count);
    }

    @Test
    void countByStudentIdAndStatus_presentOnly_returnsCorrectCount() {
        int count = attendanceRepo.countByStudentIdAndStatus(student.getId(), AttendanceStatus.PRESENT);
        assertEquals(2, count);
    }

    @Test
    void countByStudentIdAndStatus_absentOnly_returnsCorrectCount() {
        int count = attendanceRepo.countByStudentIdAndStatus(student.getId(), AttendanceStatus.ABSENT);
        assertEquals(1, count);
    }

    @Test
    void countByStatus_presentStatusAcrossAllStudents_returnsCorrectCount() {
        int count = attendanceRepo.countByStatus(AttendanceStatus.PRESENT);
        assertEquals(2, count);
    }
}
