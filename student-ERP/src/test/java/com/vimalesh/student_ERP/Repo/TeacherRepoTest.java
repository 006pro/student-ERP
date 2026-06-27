package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TeacherRepoTest {

    @Autowired TeacherRepo teacherRepo;

    @BeforeEach
    void setUp() {
        Teacher t1 = new Teacher();
        t1.setName("Mr. Smith");
        t1.setEmail("smith@school.com");
        t1.setPhone("9876543210");
        t1.setSubject("Mathematics");
        teacherRepo.save(t1);

        Teacher t2 = new Teacher();
        t2.setName("Ms. Jane");
        t2.setEmail("jane@school.com");
        t2.setPhone("9123456789");
        t2.setSubject("Physics");
        teacherRepo.save(t2);

        Teacher t3 = new Teacher();
        t3.setName("Mr. Brown");
        t3.setEmail("brown@school.com");
        t3.setPhone("9000000000");
        t3.setSubject("Mathematics");
        teacherRepo.save(t3);
    }

    @Test
    void findBySubject_exactMatch_returnsTeachersOfThatSubject() {
        List<Teacher> result = teacherRepo.findBySubject("Mathematics");
        assertEquals(2, result.size());
    }

    @Test
    void findBySubject_partialMatch_returnsMatchingTeachers() {
        List<Teacher> result = teacherRepo.findBySubject("Math");
        assertEquals(2, result.size());
    }

    @Test
    void findBySubject_noMatch_returnsEmptyList() {
        List<Teacher> result = teacherRepo.findBySubject("Chemistry");
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_returnsAllTeachers() {
        List<Teacher> result = teacherRepo.findAll();
        assertEquals(3, result.size());
    }

    @Test
    void deleteById_existingId_removesTeacher() {
        Teacher teacher = teacherRepo.findAll().get(0);
        int id = teacher.getId();
        teacherRepo.deleteById(id);
        assertFalse(teacherRepo.existsById(id));
    }
}
