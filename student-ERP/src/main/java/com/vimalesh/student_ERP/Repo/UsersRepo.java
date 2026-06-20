package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, Integer> {
}
