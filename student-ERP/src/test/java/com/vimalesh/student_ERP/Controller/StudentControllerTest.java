package com.vimalesh.student_ERP.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vimalesh.student_ERP.DTO.Request.Student.StudentRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Security.CustomUserDetailsService;
import com.vimalesh.student_ERP.Security.JwtUtil;
import com.vimalesh.student_ERP.Security.OAuth2SuccessHandler;
import com.vimalesh.student_ERP.Service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean StudentService studentService;
    @MockBean JwtUtil jwtUtil;
    @MockBean CustomUserDetailsService customUserDetailsService;
    @MockBean OAuth2SuccessHandler oAuth2SuccessHandler;

    private StudentResponseDTO sampleResponse() {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(1);
        dto.setName("Alice");
        dto.setRollNo("R001");
        dto.setClassName("10th Grade");
        dto.setSection("A");
        dto.setAttendancePercentage(85.0);
        return dto;
    }

    private StudentRequestDTO sampleRequest() {
        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName("Alice");
        dto.setRollNo("R001");
        dto.setDob(LocalDate.of(2005, 3, 10));
        dto.setParentContact("9999999999");
        dto.setAddress("Chennai");
        dto.setClassRoomId(1);
        return dto;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createStudent_validRequest_returns201() throws Exception {
        when(studentService.createStudent(any())).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.rollNo").value("R001"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createStudent_blankName_returns400() throws Exception {
        StudentRequestDTO request = sampleRequest();
        request.setName("");

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateStudent_validRequest_returns200() throws Exception {
        when(studentService.updateStudent(eq(1), any())).thenReturn(sampleResponse());

        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteStudent_existingId_returns204() throws Exception {
        doNothing().when(studentService).deleteStudent(1);

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_existingId_returns200() throws Exception {
        when(studentService.getStudentById(1)).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_defaultPagination_returns200() throws Exception {
        when(studentService.getAllStudents(any()))
                .thenReturn(new PageImpl<>(List.of(sampleResponse()), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Alice"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void searchStudents_withKeyword_returns200() throws Exception {
        when(studentService.searchStudents("Alice")).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/students/search").param("keyword", "Alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getByClass_existingClassId_returns200() throws Exception {
        when(studentService.getStudentsByClass(1)).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/students/class/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rollNo").value("R001"));
    }
}
