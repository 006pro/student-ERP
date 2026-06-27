package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.ClassRoom;
import com.vimalesh.student_ERP.Entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepoTest {

    @Autowired StudentRepo studentRepo;
    @Autowired ClassRoomRepo classRoomRepo;

    private ClassRoom savedClassRoom;

    @BeforeEach
    void setUp() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setName("10th Grade");
        classRoom.setSection("A");
        savedClassRoom = classRoomRepo.save(classRoom);

        Student s1 = new Student();
        s1.setName("Alice Smith");
        s1.setRollNo("R001");
        s1.setDob(LocalDate.of(2005, 3, 10));
        s1.setParentContact("9999999999");
        s1.setClassRoom(savedClassRoom);
        studentRepo.save(s1);

        Student s2 = new Student();
        s2.setName("Bob Jones");
        s2.setRollNo("R002");
        s2.setDob(LocalDate.of(2005, 6, 20));
        s2.setParentContact("8888888888");
        s2.setClassRoom(savedClassRoom);
        studentRepo.save(s2);
    }

    @Test
    void findByNameContaining_matchingKeyword_returnsMatchingStudents() {
        List<Student> result = studentRepo.findByNameContaining("Alice");
        assertEquals(1, result.size());
        assertEquals("Alice Smith", result.get(0).getName());
    }

    @Test
    void findByNameContaining_partialMatch_returnsMultiple() {
        List<Student> result = studentRepo.findByNameContaining("o");
        assertEquals(1, result.size());
        assertEquals("Bob Jones", result.get(0).getName());
    }

    @Test
    void findByNameContaining_noMatch_returnsEmptyList() {
        List<Student> result = studentRepo.findByNameContaining("XYZ_NOT_FOUND");
        assertTrue(result.isEmpty());
    }

    @Test
    void findByClassRoom_Id_returnsStudentsInClass() {
        List<Student> result = studentRepo.findByClassRoom_Id(savedClassRoom.getId());
        assertEquals(2, result.size());
    }

    @Test
    void findByClassRoom_Id_nonExistingClassId_returnsEmptyList() {
        List<Student> result = studentRepo.findByClassRoom_Id(9999);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_newStudent_persistsToDatabase() {
        Student student = new Student();
        student.setName("Charlie");
        student.setRollNo("R003");
        student.setParentContact("7777777777");
        student.setClassRoom(savedClassRoom);
        Student saved = studentRepo.save(student);

        assertNotNull(saved.getId());
        assertEquals("Charlie", studentRepo.findById(saved.getId()).get().getName());
    }
}
