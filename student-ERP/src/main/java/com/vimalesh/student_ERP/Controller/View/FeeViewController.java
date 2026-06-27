package com.vimalesh.student_ERP.Controller.View;

import com.vimalesh.student_ERP.DTO.Request.Fee.FeeRequestDTO;
import com.vimalesh.student_ERP.Service.FeeService;
import com.vimalesh.student_ERP.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fees")
public class FeeViewController {
    @Autowired
    private FeeService feeService;
    @Autowired
    StudentService studentService;

    @GetMapping
    public String feesPage(Model model) {
        model.addAttribute("pendingFees", feeService.getPendingFees());
        model.addAttribute("overdueFees", feeService.getOverdueFees());
        model.addAttribute("students", studentService.getAllStudents(PageRequest.of(0,10)).getContent());
        model.addAttribute("newFee", new FeeRequestDTO());
        return "fees/index";
    }

    @PostMapping("/create")
    public String createFee(@Valid @ModelAttribute("newFee") FeeRequestDTO dto) {
        feeService.createFeeRecord(dto);
        return "redirect:/fees";
    }

    @PostMapping("/pay/{id}")
    public String markAsPaid(@PathVariable int id) {
        feeService.markAsPaid(id);
        return "redirect:/fees";
    }
}