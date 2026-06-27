package com.vimalesh.student_ERP.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vimalesh.student_ERP.DTO.Request.Fee.FeeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Fee.FeeResponseDTO;
import com.vimalesh.student_ERP.Entity.Enum.FeeStatus;
import com.vimalesh.student_ERP.Security.CustomUserDetailsService;
import com.vimalesh.student_ERP.Security.JwtUtil;
import com.vimalesh.student_ERP.Security.OAuth2SuccessHandler;
import com.vimalesh.student_ERP.Service.FeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeeController.class)
class FeeControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean FeeService feeService;
    @MockBean JwtUtil jwtUtil;
    @MockBean CustomUserDetailsService customUserDetailsService;
    @MockBean OAuth2SuccessHandler oAuth2SuccessHandler;

    private FeeResponseDTO sampleResponse(FeeStatus status) {
        FeeResponseDTO dto = new FeeResponseDTO();
        dto.setId(1);
        dto.setStudentName("Alice");
        dto.setAmount(5000.0);
        dto.setDueDate(LocalDate.now().plusDays(30));
        dto.setStatus(status);
        return dto;
    }

    private FeeRequestDTO sampleRequest() {
        FeeRequestDTO dto = new FeeRequestDTO();
        dto.setStudentId(1);
        dto.setAmount(5000.0);
        dto.setDueDate(LocalDate.now().plusDays(30));
        return dto;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createFeeRecord_validRequest_returns201() throws Exception {
        when(feeService.createFeeRecord(any())).thenReturn(sampleResponse(FeeStatus.PENDING));

        mockMvc.perform(post("/api/fees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(5000.0))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createFeeRecord_pastDueDate_returns400() throws Exception {
        FeeRequestDTO request = sampleRequest();
        request.setDueDate(LocalDate.now().minusDays(1));

        mockMvc.perform(post("/api/fees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void markAsPaid_existingId_returns200() throws Exception {
        when(feeService.markAsPaid(1)).thenReturn(sampleResponse(FeeStatus.PAID));

        mockMvc.perform(patch("/api/fees/1/pay"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getFeesByStudent_returns200WithList() throws Exception {
        when(feeService.getFeesByStudent(1)).thenReturn(List.of(sampleResponse(FeeStatus.PENDING)));

        mockMvc.perform(get("/api/fees/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentName").value("Alice"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getPendingFees_returns200WithPendingList() throws Exception {
        when(feeService.getPendingFees()).thenReturn(List.of(sampleResponse(FeeStatus.PENDING)));

        mockMvc.perform(get("/api/fees/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getOverdueFees_returns200WithOverdueList() throws Exception {
        when(feeService.getOverdueFees()).thenReturn(List.of(sampleResponse(FeeStatus.OVERDUE)));

        mockMvc.perform(get("/api/fees/overdue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("OVERDUE"));
    }
}
