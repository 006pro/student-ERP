package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepo extends JpaRepository<Fee, Integer> {
}
