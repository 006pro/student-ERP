package com.vimalesh.student_ERP.Controller.View;

import com.vimalesh.student_ERP.DTO.Request.Attendance.AttendanceRequestDTO;
import com.vimalesh.student_ERP.Service.AttendanceService;
import com.vimalesh.student_ERP.Service.ClassRoomService;
import com.vimalesh.student_ERP.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceViewController {
    @Autowired
    private  AttendanceService attendanceService;
    @Autowired
    private  ClassRoomService classRoomService;
    @Autowired
    private  StudentService studentService;

    @GetMapping
    public String attendancePage(Model model) {
        model.addAttribute("classRooms", classRoomService.getAllClassRooms());
        return "attendance/index";
    }

    @GetMapping("/mark")
    public String markPage(@RequestParam int classRoomId, @RequestParam LocalDate date, Model model) {
        model.addAttribute("students", studentService.getStudentsByClass(classRoomId));
        model.addAttribute("classRoomId", classRoomId);
        model.addAttribute("date", date);
        return "attendance/mark";
    }

    @PostMapping("/save")
    public String saveAttendance(@ModelAttribute List<AttendanceRequestDTO> attendanceList) {
        attendanceService.markBulkAttendance(attendanceList);
        return "redirect:/attendance?success=true";
    }
}