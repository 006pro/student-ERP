package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Teacher;
import com.vimalesh.student_ERP.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepo extends JpaRepository<Teacher, Integer> {
}
