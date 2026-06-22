package com.vimalesh.student_ERP.Repo;

import com.vimalesh.student_ERP.DTO.Response.Attendance.AttendanceResponseDTO;
import com.vimalesh.student_ERP.Entity.Attendance;
import com.vimalesh.student_ERP.Entity.Enum.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {



    @Query("SELECT a FROM Attendance a WHERE a.classRoom.id = :classRoomId AND a.date = :date")
    List<Attendance> getAttendanceByClassAndDate(int classRoomId, LocalDate date);


    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId")
    int countByStudentId(int studentId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = :attendanceStatus")
    int countByStudentIdAndStatus(int studentId, AttendanceStatus attendanceStatus);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.status = :attendanceStatus")
    int countByStatus(AttendanceStatus attendanceStatus);
}
