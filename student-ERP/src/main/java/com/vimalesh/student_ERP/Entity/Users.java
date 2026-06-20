package com.vimalesh.student_ERP.Entity;

import com.vimalesh.student_ERP.Entity.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;
    private String oauthProvider;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    private Student student;

    @OneToOne
    private Teacher teacher;
}
