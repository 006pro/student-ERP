package com.vimalesh.student_ERP.Controller.View;

import com.vimalesh.student_ERP.Entity.Student;
import com.vimalesh.student_ERP.Entity.Users;
import com.vimalesh.student_ERP.Repo.GradeRepo;
import com.vimalesh.student_ERP.Repo.UsersRepo;
import com.vimalesh.student_ERP.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student-portal")
public class StudentPortalViewController {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private GradeRepo gradeRepo;
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public String portal(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepo.findByEmail(email);

        if (user == null || user.getStudent() == null) {
            model.addAttribute("error", "No student profile is linked to your account. Please contact an administrator.");
            return "student-portal/index";
        }

        Student student = user.getStudent();
        int studentId = student.getId();

        model.addAttribute("student", student);
        model.addAttribute("classroom", student.getClassRoom());
        model.addAttribute("grades", gradeRepo.findAllByStudentId(studentId));
        model.addAttribute("attendance", student.getAttendanceRecords());
        model.addAttribute("attendancePercent", attendanceService.getAttendancePercentage(studentId));

        return "student-portal/index";
    }
}
