package com.vimalesh.student_ERP.Controller.View;

import com.vimalesh.student_ERP.DTO.Request.Grade.GradeRequestDTO;
import com.vimalesh.student_ERP.Service.GradeService;
import com.vimalesh.student_ERP.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grades")
public class GradeViewController {
    @Autowired
    private GradeService gradeService;
    @Autowired
    private StudentService studentService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("students", studentService.getAllStudents(PageRequest.of(0,10)).getContent());
        return "grades/index";
    }

    @GetMapping("/student/{studentId}")
    public String studentGrades(@PathVariable int studentId, Model model) {
        model.addAttribute("grades", gradeService.getGradesByStudent(studentId));
        model.addAttribute("studentId", studentId);

        GradeRequestDTO newGrade = new GradeRequestDTO();
        newGrade.setStudentId(studentId);
        model.addAttribute("newGrade", newGrade);
        return "grades/student-grades";
    }

    @PostMapping("/save")
    public String saveGrade(@Valid @ModelAttribute("newGrade") GradeRequestDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("grades", gradeService.getGradesByStudent(dto.getStudentId()));
            model.addAttribute("studentId", dto.getStudentId());
            return "grades/student-grades";
        }
        gradeService.saveGrade(dto);
        return "redirect:/grades/student/" + dto.getStudentId();
    }
}