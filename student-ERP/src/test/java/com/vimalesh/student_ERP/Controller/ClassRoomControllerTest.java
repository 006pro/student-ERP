package com.vimalesh.student_ERP.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vimalesh.student_ERP.DTO.Request.Classroom.ClassRoomRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Classroom.ClassRoomResponseDTO;
import com.vimalesh.student_ERP.Security.CustomUserDetailsService;
import com.vimalesh.student_ERP.Security.JwtUtil;
import com.vimalesh.student_ERP.Security.OAuth2SuccessHandler;
import com.vimalesh.student_ERP.Service.ClassRoomService;
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

@WebMvcTest(ClassRoomController.class)
class ClassRoomControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean ClassRoomService classRoomService;
    @MockBean JwtUtil jwtUtil;
    @MockBean CustomUserDetailsService customUserDetailsService;
    @MockBean OAuth2SuccessHandler oAuth2SuccessHandler;

    private ClassRoomResponseDTO sampleResponse() {
        ClassRoomResponseDTO dto = new ClassRoomResponseDTO();
        dto.setId(1);
        dto.setName("10th Grade");
        dto.setSection("A");
        dto.setTeacherName("Mr. Smith");
        dto.setTotalStudents(30);
        return dto;
    }

    private ClassRoomRequestDTO sampleRequest() {
        ClassRoomRequestDTO dto = new ClassRoomRequestDTO();
        dto.setName("10th Grade");
        dto.setSection("A");
        dto.setTeacherId(1);
        return dto;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createClassRoom_validRequest_returns201() throws Exception {
        when(classRoomService.createClassRoom(any())).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("10th Grade"))
                .andExpect(jsonPath("$.section").value("A"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createClassRoom_blankName_returns400() throws Exception {
        ClassRoomRequestDTO request = sampleRequest();
        request.setName("");

        mockMvc.perform(post("/api/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateClassRoom_validRequest_returns200() throws Exception {
        when(classRoomService.updateClassRoom(eq(1), any())).thenReturn(sampleResponse());

        mockMvc.perform(put("/api/classrooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteClassRoom_existingId_returns204() throws Exception {
        doNothing().when(classRoomService).deleteClassRoom(1);

        mockMvc.perform(delete("/api/classrooms/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_existingId_returns200() throws Exception {
        when(classRoomService.getClassRoomById(1)).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/classrooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teacherName").value("Mr. Smith"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_returns200WithList() throws Exception {
        when(classRoomService.getAllClassRooms()).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/classrooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].totalStudents").value(30));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void assignTeacher_validIds_returns204() throws Exception {
        doNothing().when(classRoomService).assignTeacher(1, 2);

        mockMvc.perform(patch("/api/classrooms/1/assign-teacher/2"))
                .andExpect(status().isNoContent());
    }
}
