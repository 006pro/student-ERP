package com.vimalesh.student_ERP.Controller.View;

import com.vimalesh.student_ERP.DTO.Request.Teacher.TeacherRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Teacher.TeacherResponseDTO;
import com.vimalesh.student_ERP.Service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teachers")
public class TeacherViewController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "teachers/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("teacher", new TeacherRequestDTO());
        model.addAttribute("teacherId", null);
        return "teachers/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        TeacherResponseDTO existing = teacherService.getTeacherById(id);
        TeacherRequestDTO formDto = new TeacherRequestDTO();
        formDto.setName(existing.getName());
        formDto.setEmail(existing.getEmail());
        formDto.setPhone(existing.getPhone());
        formDto.setSubject(existing.getSubject());
        model.addAttribute("teacher", formDto);
        model.addAttribute("teacherId", id);
        return "teachers/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("teacher") TeacherRequestDTO dto,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("teacherId", null);
            return "teachers/form";
        }
        teacherService.createTeacher(dto);
        return "redirect:/teachers";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id,
                         @Valid @ModelAttribute("teacher") TeacherRequestDTO dto,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("teacherId", id);
            return "teachers/form";
        }
        teacherService.updateTeacher(id, dto);
        return "redirect:/teachers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teachers";
    }
}
