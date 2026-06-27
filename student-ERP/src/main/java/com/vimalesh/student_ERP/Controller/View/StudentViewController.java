package com.vimalesh.student_ERP.Controller.View;

import com.vimalesh.student_ERP.DTO.Request.Student.StudentRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Service.ClassRoomService;
import com.vimalesh.student_ERP.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentViewController {

@Autowired
    private StudentService studentService;
    @Autowired
    private ClassRoomService classRoomService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", studentService.getAllStudents(PageRequest.of(0, 10)).getContent());
        return "students/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("student", new StudentRequestDTO());
        model.addAttribute("classRooms", classRoomService.getAllClassRooms());
        return "students/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        StudentResponseDTO student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("studentId", id);
        model.addAttribute("classRooms", classRoomService.getAllClassRooms());
        return "students/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") StudentRequestDTO dto,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classRooms", classRoomService.getAllClassRooms());
            return "students/form";
        }
        studentService.createStudent(dto);
        return "redirect:/students";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @Valid @ModelAttribute("student") StudentRequestDTO dto,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classRooms", classRoomService.getAllClassRooms());
            model.addAttribute("studentId", id);
            return "students/form";
        }
        studentService.updateStudent(id, dto);
        return "redirect:/students";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        model.addAttribute("students", studentService.searchStudents(keyword));
        return "students/list";
    }
}