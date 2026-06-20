package com.vimalesh.student_ERP.DTO.Response.AiInsights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIResponseDTO {
    private String answer;
    private LocalDateTime queryTime;
}
