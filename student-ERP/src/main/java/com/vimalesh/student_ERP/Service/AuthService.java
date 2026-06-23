package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Auth.JwtResponseDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.RegisterRequestDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.LoginRequestDTO;
import com.vimalesh.student_ERP.DTO.Request.Auth.UserResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vimalesh.student_ERP.Entity.Users;
import com.vimalesh.student_ERP.Repo.UsersRepo;
import com.vimalesh.student_ERP.Security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UsersRepo usersRepo;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponseDTO register(@Valid RegisterRequestDTO dto) {
        if (usersRepo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Users user = new Users();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        Users saved = usersRepo.save(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(saved.getId());
        response.setEmail(saved.getEmail());
        response.setRole(saved.getRole().name());
        return response;


    }

    public JwtResponseDTO login(LoginRequestDTO dto) {

        Users user = usersRepo.findByEmail(dto.getEmail());
        if(user == null) {
            throw new UnsupportedOperationException("Invalid email or password");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnsupportedOperationException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        JwtResponseDTO response = new JwtResponseDTO();
        response.setToken(token);
        response.setRole(user.getRole().name());
        response.setExpiresIn(86400000);
        return response;

    }

    public void logout(String token) {

    }
}
