package com.vimalesh.student_ERP.Controller.View;

import com.vimalesh.student_ERP.DTO.Request.Classroom.ClassRoomRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Classroom.ClassRoomResponseDTO;
import com.vimalesh.student_ERP.Service.ClassRoomService;
import com.vimalesh.student_ERP.Service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/classrooms")

public class ClassRoomViewController {
    @Autowired
    private ClassRoomService classRoomService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("classrooms", classRoomService.getAllClassRooms());
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "classrooms/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("classroom", new ClassRoomRequestDTO());
        model.addAttribute("classroomId", null);
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "classrooms/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        ClassRoomResponseDTO existing = classRoomService.getClassRoomById(id);
        ClassRoomRequestDTO formDto = new ClassRoomRequestDTO();
        formDto.setName(existing.getName());
        formDto.setSection(existing.getSection());

        model.addAttribute("classroom", formDto);
        model.addAttribute("classroomId", id);
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "classrooms/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("classroom") ClassRoomRequestDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classroomId", null);
            model.addAttribute("teachers", teacherService.getAllTeachers());
            return "classrooms/form";
        }
        classRoomService.createClassRoom(dto);
        return "redirect:/classrooms";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @Valid @ModelAttribute("classroom") ClassRoomRequestDTO dto,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classroomId", id);
            model.addAttribute("teachers", teacherService.getAllTeachers());
            return "classrooms/form";
        }
        classRoomService.updateClassRoom(id, dto);
        return "redirect:/classrooms";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        classRoomService.deleteClassRoom(id);
        return "redirect:/classrooms";
    }

    @PostMapping("/assign-teacher/{id}")
    public String assignTeacher(@PathVariable int id, @RequestParam int teacherId) {
        classRoomService.assignTeacher(id, teacherId);
        return "redirect:/classrooms";
    }
}