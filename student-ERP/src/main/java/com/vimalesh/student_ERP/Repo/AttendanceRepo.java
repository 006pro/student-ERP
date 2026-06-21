package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {
}
