package com.vimalesh.student_ERP.Controller.View;

import com.vimalesh.student_ERP.DTO.Request.Attendance.AttendanceRequestDTO;
import com.vimalesh.student_ERP.Entity.Enum.AttendanceStatus;
import com.vimalesh.student_ERP.Service.AttendanceService;
import com.vimalesh.student_ERP.Service.ClassRoomService;
import com.vimalesh.student_ERP.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public String index(@RequestParam(required = false) Boolean success, Model model) {
        model.addAttribute("classRooms", classRoomService.getAllClassRooms());
        model.addAttribute("success", success);
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
    public String saveAttendance(@RequestParam int classRoomId,
                                 @RequestParam LocalDate date,
                                 @RequestParam(required = false) List<Integer> presentStudentIds,
                                 @RequestParam List<Integer> allStudentIds) {
        Set<Integer> presentSet = presentStudentIds == null ? Set.of() : new HashSet<>(presentStudentIds);
        List<AttendanceRequestDTO> list = new ArrayList<>();

        for (int studentId : allStudentIds) {
            AttendanceRequestDTO dto = new AttendanceRequestDTO();
            dto.setStudentId(studentId);
            dto.setClassRoomId(classRoomId);
            dto.setDate(date);
            dto.setStatus(presentSet.contains(studentId) ? AttendanceStatus.PRESENT : AttendanceStatus.ABSENT);
            list.add(dto);
        }

        attendanceService.markBulkAttendance(list);
        return "redirect:/attendance?success=true";
    }
}