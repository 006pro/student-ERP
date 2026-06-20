package com.vimalesh.student_ERP.DTO.Request.AiInsights;

import jakarta.validation.constraints.NotBlank;

public class AIQueryRequestDTO {
    @NotBlank
    private String question;
}
