package com.vimalesh.student_ERP.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vimalesh.student_ERP.DTO.Request.Auth.JwtResponseDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.LoginRequestDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.RegisterRequestDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.UserResponseDTO;
import com.vimalesh.student_ERP.Entity.Enum.Role;
import com.vimalesh.student_ERP.Security.CustomUserDetailsService;
import com.vimalesh.student_ERP.Security.JwtUtil;
import com.vimalesh.student_ERP.Security.OAuth2SuccessHandler;
import com.vimalesh.student_ERP.Service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean AuthService authService;
    @MockBean JwtUtil jwtUtil;
    @MockBean CustomUserDetailsService customUserDetailsService;
    @MockBean OAuth2SuccessHandler oAuth2SuccessHandler;

    @Test
    void register_validRequest_returns201AndUserResponse() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO("admin@school.com", "password123", Role.ADMIN);
        UserResponseDTO response = new UserResponseDTO(1, "admin@school.com", "ADMIN");

        when(authService.register(any())).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("admin@school.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void register_invalidEmail_returns400() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO("not-an-email", "password123", Role.ADMIN);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_shortPassword_returns400() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO("admin@school.com", "abc", Role.ADMIN);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_validRequest_returns200AndToken() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("admin@school.com", "password123");
        JwtResponseDTO response = new JwtResponseDTO("jwt.token.here", "ADMIN", 86400000);

        when(authService.login(any())).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt.token.here"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void login_blankEmail_returns400() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("", "password");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void logout_withBearerToken_returns204() throws Exception {
        mockMvc.perform(post("/api/auth/logout")
                        .header("Authorization", "Bearer some.jwt.token"))
                .andExpect(status().isNoContent());
    }
}
