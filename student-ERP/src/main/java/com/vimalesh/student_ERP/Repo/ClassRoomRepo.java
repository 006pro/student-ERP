package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.ClassRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRoomRepo extends JpaRepository<ClassRoom, Integer> {
}
