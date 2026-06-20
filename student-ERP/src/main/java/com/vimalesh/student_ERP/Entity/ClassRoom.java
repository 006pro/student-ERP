package com.vimalesh.student_ERP.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String section;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;             // class has ONE class-teacher

    @OneToMany(mappedBy = "classRoom")
    private List<Student> students;
}

