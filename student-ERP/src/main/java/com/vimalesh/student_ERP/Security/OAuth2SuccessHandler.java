package com.vimalesh.student_ERP.Security;


import com.vimalesh.student_ERP.Entity.Enum.Role;
import com.vimalesh.student_ERP.Entity.Users;
import com.vimalesh.student_ERP.Repo.UsersRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UsersRepo userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Users user = userRepository.findByEmail(email);
        if (user == null) {
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setOauthProvider("GOOGLE");
            newUser.setRole(Role.STUDENT);
            user = userRepository.save(newUser);
        }


        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
//        response.getWriter().write(token);
  response.sendRedirect("/dashboard?token=" + token);
    }
}