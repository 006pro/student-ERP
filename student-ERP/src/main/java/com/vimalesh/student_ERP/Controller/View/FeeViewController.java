package com.vimalesh.student_ERP.Controller.View;

import com.vimalesh.student_ERP.Service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fees")
public class FeeViewController {
    @Autowired
    private FeeService feeService;

    @GetMapping
    public String feesPage(Model model) {
        model.addAttribute("pendingFees", feeService.getPendingFees());
        model.addAttribute("overdueFees", feeService.getOverdueFees());
        return "fees/index";
    }

    @PostMapping("/pay/{id}")
    public String markAsPaid(@PathVariable int id) {
        feeService.markAsPaid(id);
        return "redirect:/fees";
    }
}