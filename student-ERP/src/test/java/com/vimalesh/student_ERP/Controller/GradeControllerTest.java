package com.vimalesh.student_ERP.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vimalesh.student_ERP.DTO.Request.Grade.GradeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Grade.GradeResponseDTO;
import com.vimalesh.student_ERP.Security.CustomUserDetailsService;
import com.vimalesh.student_ERP.Security.JwtUtil;
import com.vimalesh.student_ERP.Security.OAuth2SuccessHandler;
import com.vimalesh.student_ERP.Service.GradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GradeController.class)
class GradeControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean GradeService gradeService;
    @MockBean JwtUtil jwtUtil;
    @MockBean CustomUserDetailsService customUserDetailsService;
    @MockBean OAuth2SuccessHandler oAuth2SuccessHandler;

    private GradeResponseDTO sampleResponse() {
        GradeResponseDTO dto = new GradeResponseDTO();
        dto.setId(1);
        dto.setStudentName("Alice");
        dto.setSubject("Mathematics");
        dto.setMarks(85.0);
        dto.setMaxMarks(100.0);
        dto.setGrade("A");
        dto.setTerm("Term1");
        return dto;
    }

    private GradeRequestDTO sampleRequest() {
        GradeRequestDTO dto = new GradeRequestDTO();
        dto.setStudentId(1);
        dto.setSubject("Mathematics");
        dto.setMarks(85.0);
        dto.setMaxMarks(100.0);
        dto.setTerm("Term1");
        return dto;
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void saveGrade_validRequest_returns201() throws Exception {
        when(gradeService.saveGrade(any())).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.grade").value("A"))
                .andExpect(jsonPath("$.subject").value("Mathematics"));
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void saveGrade_blankSubject_returns400() throws Exception {
        GradeRequestDTO request = sampleRequest();
        request.setSubject("");

        mockMvc.perform(post("/api/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void saveBulkGrades_validList_returns201() throws Exception {
        doNothing().when(gradeService).saveBulkGrades(any());

        mockMvc.perform(post("/api/grades/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(sampleRequest()))))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void getByStudent_existingStudentId_returns200() throws Exception {
        when(gradeService.getGradesByStudent(1)).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/grades/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentName").value("Alice"));
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void getByStudentAndTerm_returns200() throws Exception {
        when(gradeService.getGradesByStudentAndTerm(1, "Term1")).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/grades/student/1/term/Term1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].term").value("Term1"));
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void getAverage_returns200WithValue() throws Exception {
        when(gradeService.calculateAverage(1, "Term1")).thenReturn(85.0);

        mockMvc.perform(get("/api/grades/student/1/average").param("term", "Term1"))
                .andExpect(status().isOk())
                .andExpect(content().string("85.0"));
    }
}
