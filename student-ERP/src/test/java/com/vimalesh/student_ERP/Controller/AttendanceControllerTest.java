package com.vimalesh.student_ERP.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vimalesh.student_ERP.DTO.Request.Attendance.AttendanceRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Attendance.AttendanceResponseDTO;
import com.vimalesh.student_ERP.DTO.Response.Student.StudentResponseDTO;
import com.vimalesh.student_ERP.Entity.Enum.AttendanceStatus;
import com.vimalesh.student_ERP.Security.CustomUserDetailsService;
import com.vimalesh.student_ERP.Security.JwtUtil;
import com.vimalesh.student_ERP.Security.OAuth2SuccessHandler;
import com.vimalesh.student_ERP.Service.AttendanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AttendanceController.class)
class AttendanceControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean AttendanceService attendanceService;
    @MockitoBean JwtUtil jwtUtil;
    @MockitoBean CustomUserDetailsService customUserDetailsService;
    @MockitoBean OAuth2SuccessHandler oAuth2SuccessHandler;

    private AttendanceRequestDTO sampleRequest() {
        AttendanceRequestDTO dto = new AttendanceRequestDTO();
        dto.setStudentId(1);
        dto.setClassRoomId(1);
        dto.setDate(LocalDate.now().minusDays(1));
        dto.setStatus(AttendanceStatus.PRESENT);
        return dto;
    }

    private AttendanceResponseDTO sampleResponse() {
        AttendanceResponseDTO dto = new AttendanceResponseDTO();
        dto.setId(1);
        dto.setStudentName("Alice");
        dto.setRollNo("R001");
        dto.setDate(LocalDate.now().minusDays(1));
        dto.setStatus(AttendanceStatus.PRESENT);
        return dto;
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void markAttendance_validRequest_returns201() throws Exception {
        doNothing().when(attendanceService).markAttendance(any());

        mockMvc.perform(post("/api/attendance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void markBulkAttendance_validList_returns201() throws Exception {
        doNothing().when(attendanceService).markBulkAttendance(any());

        mockMvc.perform(post("/api/attendance/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(sampleRequest()))))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void getByStudent_existingStudentId_returns200() throws Exception {
        when(attendanceService.getAttendanceByStudent(1)).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/attendance/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentName").value("Alice"));
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void getByClassAndDate_returns200() throws Exception {
        when(attendanceService.getAttendanceByClassAndDate(eq(1), any()))
                .thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/attendance/class/1")
                        .param("date", LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("PRESENT"));
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void getPercentage_existingStudentId_returns200() throws Exception {
        when(attendanceService.getAttendancePercentage(1)).thenReturn(85.0);

        mockMvc.perform(get("/api/attendance/student/1/percentage"))
                .andExpect(status().isOk())
                .andExpect(content().string("85.0"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getBelowThreshold_returns200WithFilteredStudents() throws Exception {
        StudentResponseDTO student = new StudentResponseDTO();
        student.setName("Bob");
        student.setAttendancePercentage(60.0);
        when(attendanceService.getStudentsBelowThreshold(75.0)).thenReturn(List.of(student));

        mockMvc.perform(get("/api/attendance/below-threshold").param("percent", "75.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bob"));
    }
}
