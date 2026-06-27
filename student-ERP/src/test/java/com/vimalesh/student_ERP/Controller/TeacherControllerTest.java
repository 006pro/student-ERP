package com.vimalesh.student_ERP.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vimalesh.student_ERP.DTO.Request.Teacher.TeacherRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Teacher.TeacherResponseDTO;
import com.vimalesh.student_ERP.Security.CustomUserDetailsService;
import com.vimalesh.student_ERP.Security.JwtUtil;
import com.vimalesh.student_ERP.Security.OAuth2SuccessHandler;
import com.vimalesh.student_ERP.Service.TeacherService;
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

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean TeacherService teacherService;
    @MockBean JwtUtil jwtUtil;
    @MockBean CustomUserDetailsService customUserDetailsService;
    @MockBean OAuth2SuccessHandler oAuth2SuccessHandler;

    private TeacherResponseDTO sampleResponse() {
        TeacherResponseDTO dto = new TeacherResponseDTO();
        dto.setId(1);
        dto.setName("Mr. Smith");
        dto.setEmail("smith@school.com");
        dto.setPhone("9876543210");
        dto.setSubject("Mathematics");
        return dto;
    }

    private TeacherRequestDTO sampleRequest() {
        TeacherRequestDTO dto = new TeacherRequestDTO();
        dto.setName("Mr. Smith");
        dto.setEmail("smith@school.com");
        dto.setPhone("9876543210");
        dto.setSubject("Mathematics");
        return dto;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createTeacher_validRequest_returns201() throws Exception {
        when(teacherService.createTeacher(any())).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mr. Smith"))
                .andExpect(jsonPath("$.subject").value("Mathematics"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createTeacher_invalidEmail_returns400() throws Exception {
        TeacherRequestDTO request = sampleRequest();
        request.setEmail("not-an-email");

        mockMvc.perform(post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTeacher_validRequest_returns200() throws Exception {
        when(teacherService.updateTeacher(eq(1), any())).thenReturn(sampleResponse());

        mockMvc.perform(put("/api/teachers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteTeacher_existingId_returns204() throws Exception {
        doNothing().when(teacherService).deleteTeacher(1);

        mockMvc.perform(delete("/api/teachers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_existingId_returns200() throws Exception {
        when(teacherService.getTeacherById(1)).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/teachers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mr. Smith"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_returns200WithList() throws Exception {
        when(teacherService.getAllTeachers()).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("smith@school.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getBySubject_returns200WithMatchingTeachers() throws Exception {
        when(teacherService.getTeachersBySubject("Mathematics")).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/teachers/subject/Mathematics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].subject").value("Mathematics"));
    }
}
