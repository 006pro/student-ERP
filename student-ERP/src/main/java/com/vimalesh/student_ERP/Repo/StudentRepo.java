package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword%")
    List<Student> findByNameContaining(String keyword);


    @Query( "SELECT s FROM Student s WHERE s.classRoom.id = :classRoomId")
    List<Student> findByClassRoom_Id(int classRoomId);
}
