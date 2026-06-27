package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Enum.Role;
import com.vimalesh.student_ERP.Entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsersRepoTest {

    @Autowired UsersRepo usersRepo;

    @BeforeEach
    void setUp() {
        Users admin = new Users();
        admin.setEmail("admin@school.com");
        admin.setPassword("hashed_password");
        admin.setRole(Role.ADMIN);
        usersRepo.save(admin);

        Users teacher = new Users();
        teacher.setEmail("teacher@school.com");
        teacher.setPassword("hashed_password");
        teacher.setRole(Role.TEACHER);
        usersRepo.save(teacher);
    }

    @Test
    void findByEmail_existingEmail_returnsUser() {
        Users user = usersRepo.findByEmail("admin@school.com");
        assertNotNull(user);
        assertEquals("admin@school.com", user.getEmail());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void findByEmail_nonExistingEmail_returnsNull() {
        Users user = usersRepo.findByEmail("unknown@school.com");
        assertNull(user);
    }

    @Test
    void existsByEmail_existingEmail_returnsTrue() {
        assertTrue(usersRepo.existsByEmail("teacher@school.com"));
    }

    @Test
    void existsByEmail_nonExistingEmail_returnsFalse() {
        assertFalse(usersRepo.existsByEmail("nobody@school.com"));
    }

    @Test
    void save_newUser_persistsToDatabase() {
        Users newUser = new Users();
        newUser.setEmail("student@school.com");
        newUser.setPassword("hashed");
        newUser.setRole(Role.STUDENT);
        Users saved = usersRepo.save(newUser);

        assertNotNull(saved.getId());
        assertTrue(saved.getId() > 0);
    }

    @Test
    void findAll_returnsAllSavedUsers() {
        assertEquals(2, usersRepo.findAll().size());
    }
}
