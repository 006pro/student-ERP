package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepo extends JpaRepository<Grade, Integer> {
}
