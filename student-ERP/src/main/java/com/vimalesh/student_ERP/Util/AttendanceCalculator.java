package com.vimalesh.student_ERP.Util;

public class AttendanceCalculator {

    public static double calculatePercentage(int presentDays, int totalDays) {
        if (totalDays == 0) return 0.0;
        return Math.round((presentDays * 100.0 / totalDays) * 100) / 100.0;
    }

    public static boolean isBelowThreshold(double percentage, double threshold) {
        return percentage < threshold;
    }
}
