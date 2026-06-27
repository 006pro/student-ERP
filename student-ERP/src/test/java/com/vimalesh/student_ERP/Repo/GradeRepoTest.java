package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Grade;
import com.vimalesh.student_ERP.Entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GradeRepoTest {

    @Autowired GradeRepo gradeRepo;
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

        Grade g1 = new Grade();
        g1.setSubject("Mathematics");
        g1.setMarks(85.0);
        g1.setMaxMarks(100.0);
        g1.setTerm("Term1");
        g1.setStudent(student);
        gradeRepo.save(g1);

        Grade g2 = new Grade();
        g2.setSubject("Physics");
        g2.setMarks(78.0);
        g2.setMaxMarks(100.0);
        g2.setTerm("Term1");
        g2.setStudent(student);
        gradeRepo.save(g2);

        Grade g3 = new Grade();
        g3.setSubject("Chemistry");
        g3.setMarks(92.0);
        g3.setMaxMarks(100.0);
        g3.setTerm("Term2");
        g3.setStudent(student);
        gradeRepo.save(g3);
    }

    @Test
    void findAllByStudentId_returnsAllGradesForStudent() {
        List<Grade> result = gradeRepo.findAllByStudentId(student.getId());
        assertEquals(3, result.size());
    }

    @Test
    void findAllByStudentId_nonExistingStudentId_returnsEmptyList() {
        List<Grade> result = gradeRepo.findAllByStudentId(9999);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllByStudentIdAndTerm_term1_returnsTwoGrades() {
        List<Grade> result = gradeRepo.findAllByStudentIdAndTerm(student.getId(), "Term1");
        assertEquals(2, result.size());
    }

    @Test
    void findAllByStudentIdAndTerm_term2_returnsOneGrade() {
        List<Grade> result = gradeRepo.findAllByStudentIdAndTerm(student.getId(), "Term2");
        assertEquals(1, result.size());
        assertEquals("Chemistry", result.get(0).getSubject());
    }

    @Test
    void findAllByStudentIdAndTerm_nonExistingTerm_returnsEmptyList() {
        List<Grade> result = gradeRepo.findAllByStudentIdAndTerm(student.getId(), "Term99");
        assertTrue(result.isEmpty());
    }
}
