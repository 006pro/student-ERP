package com.vimalesh.student_ERP.Util;

import org.springframework.stereotype.Component;

@Component
public class GradeCalculator {

    public static String calculateGrade(double marks, double maxMarks) {
        if (maxMarks <= 0) {
            throw new IllegalArgumentException("Max marks must be greater than zero");
        }

        double percent = (marks / maxMarks) * 100;

        if (percent >= 90) return "A+";
        if (percent >= 75) return "A";
        if (percent >= 60) return "B";
        if (percent >= 40) return "C";
        return "F";
    }

    public static double calculatePercentage(double marks, double maxMarks) {
        if (maxMarks <= 0) {
            throw new IllegalArgumentException("Max marks must be greater than zero");
        }
        return (marks / maxMarks) * 100;
    }
}