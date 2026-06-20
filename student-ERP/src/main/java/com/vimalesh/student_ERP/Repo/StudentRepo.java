package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Integer> {
}
