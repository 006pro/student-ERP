package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Enum.FeeStatus;
import com.vimalesh.student_ERP.Entity.Fee;
import com.vimalesh.student_ERP.Entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FeeRepoTest {

    @Autowired FeeRepo feeRepo;
    @Autowired StudentRepo studentRepo;
    @Autowired ClassRoomRepo classRoomRepo;

    private Student student;

    @BeforeEach
    void setUp() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setName("10th Grade");
        classRoom.setSection("A");
        classRoom = classRoomRepo.save(classRoom);

        student = new Student();
        student.setName("Alice");
        student.setRollNo("R001");
        student.setParentContact("9999999999");
        student.setClassRoom(classRoom);
        student = studentRepo.save(student);

        Fee f1 = new Fee();
        f1.setAmount(5000.0);
        f1.setDueDate(LocalDate.now().plusDays(30));
        f1.setStatus(FeeStatus.PENDING);
        f1.setStudent(student);
        feeRepo.save(f1);

        Fee f2 = new Fee();
        f2.setAmount(3000.0);
        f2.setDueDate(LocalDate.now().minusDays(5));
        f2.setStatus(FeeStatus.OVERDUE);
        f2.setStudent(student);
        feeRepo.save(f2);

        Fee f3 = new Fee();
        f3.setAmount(4000.0);
        f3.setDueDate(LocalDate.now().plusDays(60));
        f3.setPaidOn(LocalDate.now());
        f3.setStatus(FeeStatus.PAID);
        f3.setStudent(student);
        feeRepo.save(f3);
    }

    @Test
    void findByStudentId_returnsAllFeesForStudent() {
        List<Fee> result = feeRepo.findByStudentId(student.getId());
        assertEquals(3, result.size());
    }

    @Test
    void findByStudentId_nonExistingStudentId_returnsEmptyList() {
        List<Fee> result = feeRepo.findByStudentId(9999);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByStatus_pending_returnsOnlyPendingFees() {
        List<Fee> result = feeRepo.findByStatus(FeeStatus.PENDING);
        assertEquals(1, result.size());
        assertEquals(FeeStatus.PENDING, result.get(0).getStatus());
    }

    @Test
    void findByStatus_overdue_returnsOnlyOverdueFees() {
        List<Fee> result = feeRepo.findByStatus(FeeStatus.OVERDUE);
        assertEquals(1, result.size());
        assertEquals(3000.0, result.get(0).getAmount());
    }

    @Test
    void findByStatus_paid_returnsOnlyPaidFees() {
        List<Fee> result = feeRepo.findByStatus(FeeStatus.PAID);
        assertEquals(1, result.size());
        assertEquals(FeeStatus.PAID, result.get(0).getStatus());
    }
}
