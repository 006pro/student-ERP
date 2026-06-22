package com.vimalesh.student_ERP.Controller.View;

import com.vimalesh.student_ERP.Service.AttendanceService;
import com.vimalesh.student_ERP.Service.FeeService;
import com.vimalesh.student_ERP.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardViewController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private FeeService feeService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalStudents", studentService.getAllStudents(PageRequest.of(0,10)).getTotalElements());
        model.addAttribute("avgAttendance", attendanceService.getOverallAttendancePercentage());
        model.addAttribute("pendingFeesCount", feeService.getPendingFees().size());
        return "dashboard";
    }
}

