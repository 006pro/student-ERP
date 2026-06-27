package com.vimalesh.student_ERP.Controller;

import com.vimalesh.student_ERP.DTO.Request.Auth.JwtResponseDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.LoginRequestDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.RegisterRequestDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.UserResponseDTO;
import com.vimalesh.student_ERP.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Register, login, and logout endpoints")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(dto));
    }

    @Operation(summary = "Login and receive a JWT token")
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @Operation(summary = "Logout and invalidate the JWT token")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.noContent().build();
    }
}
