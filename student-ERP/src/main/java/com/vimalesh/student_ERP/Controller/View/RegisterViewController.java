package com.vimalesh.student_ERP.Controller.View;

import com.vimalesh.student_ERP.DTO.Request.Auth.RegisterRequestDTO;
import com.vimalesh.student_ERP.Entity.Enum.Role;
import com.vimalesh.student_ERP.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterViewController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public String registerPage(Model model) {
        model.addAttribute("roles", Role.values());
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam Role role,
                           Model model) {
        try {
            RegisterRequestDTO dto = new RegisterRequestDTO();
            dto.setEmail(email);
            dto.setPassword(password);
            dto.setRole(role);
            authService.register(dto);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", Role.values());
            return "register";
        }
    }
}
