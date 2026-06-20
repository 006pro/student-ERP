package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepo extends JpaRepository<Grade, Integer> {
}
