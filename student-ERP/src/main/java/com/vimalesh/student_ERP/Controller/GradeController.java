package com.vimalesh.student_ERP.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.vimalesh.student_ERP.DTO.Request.Grade.GradeRequestDTO;
import com.vimalesh.student_ERP.Service.GradeService;
import com.vimalesh.student_ERP.Service.StudentService;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grades")
public class GradeController {
    @Autowired
    GradeService gradeService;
    @Autowired
    StudentService studentService;

    @GetMapping
    public String gradesPage(Model model) {
        model.addAttribute("students", studentService.getAllStudents(PageRequest.of(0, 10)).getContent());
        return "grades/index";
    }

    @GetMapping("/student/{studentId}")
    public String studentGrades(@PathVariable int studentId, Model model) {
        model.addAttribute("grades", gradeService.getGradesByStudent(studentId));
        model.addAttribute("studentId", studentId);
        return "grades/student-grades";
    }

    @PostMapping("/save")
    public String saveGrade(@Valid @ModelAttribute GradeRequestDTO dto) {
        gradeService.saveGrade(dto);
        return "redirect:/grades/student/" + dto.getStudentId();
    }
}