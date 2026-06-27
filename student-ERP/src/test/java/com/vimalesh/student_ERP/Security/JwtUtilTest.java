package com.vimalesh.student_ERP.Security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    private static final String SECRET = "ThisIsA32CharacterMinimumSecretKeyForJWT1234567890";
    private static final long EXPIRATION = 86400000L;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", SECRET);
        ReflectionTestUtils.setField(jwtUtil, "expiration", EXPIRATION);
    }

    @Test
    void generateToken_validEmailAndRole_returnsNonNullToken() {
        String token = jwtUtil.generateToken("test@school.com", "ADMIN");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractEmail_validToken_returnsCorrectEmail() {
        String token = jwtUtil.generateToken("test@school.com", "ADMIN");
        assertEquals("test@school.com", jwtUtil.extractEmail(token));
    }

    @Test
    void extractRole_validToken_returnsCorrectRole() {
        String token = jwtUtil.generateToken("test@school.com", "TEACHER");
        assertEquals("TEACHER", jwtUtil.extractRole(token));
    }

    @Test
    void isTokenValid_freshToken_returnsTrue() {
        String token = jwtUtil.generateToken("user@school.com", "STUDENT");
        assertTrue(jwtUtil.isTokenValid(token));
    }

    @Test
    void isTokenValid_expiredToken_returnsFalse() {
        ReflectionTestUtils.setField(jwtUtil, "expiration", -1000L);
        String expiredToken = jwtUtil.generateToken("user@school.com", "ADMIN");
        assertFalse(jwtUtil.isTokenValid(expiredToken));
    }

    @Test
    void isTokenValid_malformedToken_returnsFalse() {
        assertFalse(jwtUtil.isTokenValid("not.a.valid.jwt.token"));
    }

    @Test
    void isTokenValid_emptyString_returnsFalse() {
        assertFalse(jwtUtil.isTokenValid(""));
    }

    @Test
    void generateToken_differentRoles_eachTokenHasCorrectRole() {
        String adminToken = jwtUtil.generateToken("admin@school.com", "ADMIN");
        String teacherToken = jwtUtil.generateToken("teacher@school.com", "TEACHER");

        assertEquals("ADMIN", jwtUtil.extractRole(adminToken));
        assertEquals("TEACHER", jwtUtil.extractRole(teacherToken));
    }
}
