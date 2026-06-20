package com.vimalesh.student_ERP.Entity;

import com.vimalesh.student_ERP.Entity.Enum.FeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double amount;
    private LocalDate dueDate;
    private LocalDate paidOn;


    @Enumerated(EnumType.STRING)
    private FeeStatus status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
