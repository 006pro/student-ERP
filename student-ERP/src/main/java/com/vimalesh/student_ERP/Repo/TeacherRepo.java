package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Teacher;
import com.vimalesh.student_ERP.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Integer> {

    @Query("SELECT t FROM Teacher t WHERE t.subject LIKE %:subject%")
    List<Teacher> findBySubject(String subject);
}
