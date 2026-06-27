package com.vimalesh.student_ERP.Controller;

import com.vimalesh.student_ERP.Security.CustomUserDetailsService;
import com.vimalesh.student_ERP.Security.JwtUtil;
import com.vimalesh.student_ERP.Security.OAuth2SuccessHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AIController.class)
class AIControllerTest {

    @Autowired MockMvc mockMvc;

    @MockitoBean OllamaChatModel ollamaChatModel;
    @MockitoBean JwtUtil jwtUtil;
    @MockitoBean CustomUserDetailsService customUserDetailsService;
    @MockitoBean OAuth2SuccessHandler oAuth2SuccessHandler;

    @BeforeEach
    void setUp() {
        ChatResponse chatResponse = mock(ChatResponse.class, RETURNS_DEEP_STUBS);
        when(chatResponse.getMetadata().getModel()).thenReturn("qwen2.5-coder:14b");
        when(chatResponse.getResult().getOutput().getText()).thenReturn("Test AI response");
        when(ollamaChatModel.call(any(Prompt.class))).thenReturn(chatResponse);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAnswer_validMessage_returns200WithResponse() throws Exception {
        mockMvc.perform(get("/api/ai/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Test AI response"));
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getAnswer_asStudent_returns200() throws Exception {
        mockMvc.perform(get("/api/ai/what is spring boot"))
                .andExpect(status().isOk());
    }
}
