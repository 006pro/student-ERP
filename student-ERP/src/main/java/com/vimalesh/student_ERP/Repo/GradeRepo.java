package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface GradeRepo extends JpaRepository<Grade, Integer> {

    @Query("SELECT g FROM Grade g WHERE g.student.id = :studentId")
    List<Grade> findAllByStudentId(int studentId);


    @Query("SELECT g FROM Grade g WHERE g.student.id = :studentId AND g.term = :term")
    List<Grade> findAllByStudentIdAndTerm(int studentId, String term);
}
