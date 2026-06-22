package com.vimalesh.student_ERP.Controller.View;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ai-insights")
public class AIViewController {

    @GetMapping
    public String aiPage() {
        return "ai/index";
    }
}