package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Auth.JwtResponseDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.LoginRequestDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.RegisterRequestDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.UserResponseDTO;
import com.vimalesh.student_ERP.Entity.Enum.Role;
import com.vimalesh.student_ERP.Entity.Users;
import com.vimalesh.student_ERP.Repo.UsersRepo;
import com.vimalesh.student_ERP.Security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock UsersRepo usersRepo;
    @Mock JwtUtil jwtUtil;
    @Mock PasswordEncoder passwordEncoder;
    @InjectMocks AuthService authService;

    @Test
    void register_newEmail_savesUserAndReturnsResponse() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setEmail("admin@school.com");
        request.setPassword("password123");
        request.setRole(Role.ADMIN);

        Users savedUser = new Users();
        savedUser.setId(1);
        savedUser.setEmail("admin@school.com");
        savedUser.setRole(Role.ADMIN);

        when(usersRepo.existsByEmail("admin@school.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashed_password");
        when(usersRepo.save(any(Users.class))).thenReturn(savedUser);

        UserResponseDTO result = authService.register(request);

        assertEquals(1, result.getId());
        assertEquals("admin@school.com", result.getEmail());
        assertEquals("ADMIN", result.getRole());
        verify(usersRepo).save(any(Users.class));
    }

    @Test
    void register_duplicateEmail_throwsRuntimeException() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setEmail("existing@school.com");
        request.setPassword("password");
        request.setRole(Role.TEACHER);

        when(usersRepo.existsByEmail("existing@school.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.register(request));
        verify(usersRepo, never()).save(any());
    }

    @Test
    void login_validCredentials_returnsJwtResponse() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("admin@school.com");
        request.setPassword("password123");

        Users user = new Users();
        user.setEmail("admin@school.com");
        user.setPassword("hashed_password");
        user.setRole(Role.ADMIN);

        when(usersRepo.findByEmail("admin@school.com")).thenReturn(user);
        when(passwordEncoder.matches("password123", "hashed_password")).thenReturn(true);
        when(jwtUtil.generateToken("admin@school.com", "ADMIN")).thenReturn("jwt_token");

        JwtResponseDTO result = authService.login(request);

        assertEquals("jwt_token", result.getToken());
        assertEquals("ADMIN", result.getRole());
        assertEquals(86400000, result.getExpiresIn());
    }

    @Test
    void login_emailNotFound_throwsUnsupportedOperationException() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("unknown@school.com");
        request.setPassword("password");

        when(usersRepo.findByEmail("unknown@school.com")).thenReturn(null);

        assertThrows(UnsupportedOperationException.class, () -> authService.login(request));
    }

    @Test
    void login_wrongPassword_throwsUnsupportedOperationException() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("admin@school.com");
        request.setPassword("wrongpassword");

        Users user = new Users();
        user.setEmail("admin@school.com");
        user.setPassword("hashed_password");
        user.setRole(Role.ADMIN);

        when(usersRepo.findByEmail("admin@school.com")).thenReturn(user);
        when(passwordEncoder.matches("wrongpassword", "hashed_password")).thenReturn(false);

        assertThrows(UnsupportedOperationException.class, () -> authService.login(request));
    }

    @Test
    void logout_doesNotThrowAndPerformsNoOperation() {
        assertDoesNotThrow(() -> authService.logout("Bearer some.jwt.token"));
    }
}
