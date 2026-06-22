package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.DTO.Response.Fee.FeeResponseDTO;
import com.vimalesh.student_ERP.Entity.Enum.FeeStatus;
import com.vimalesh.student_ERP.Entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface FeeRepo extends JpaRepository<Fee, Integer> {

    @Query("SELECT f FROM Fee f WHERE f.student.id = :studentId")
    List<Fee> findByStudentId(int studentId);


    @Query("SELECT f FROM Fee f WHERE f.status = :feeStatus")
    List<Fee> findByStatus(FeeStatus feeStatus);
}
