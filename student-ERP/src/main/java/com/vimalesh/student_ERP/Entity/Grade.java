package com.vimalesh.student_ERP.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subject;
    private double marks;
    private double maxMarks;
    private String term;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
