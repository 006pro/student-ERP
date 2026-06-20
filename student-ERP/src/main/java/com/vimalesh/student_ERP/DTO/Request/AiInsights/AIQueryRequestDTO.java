package com.vimalesh.student_ERP.DTO.Request.AiInsights;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIQueryRequestDTO {
    @NotBlank
    private String question;
}
