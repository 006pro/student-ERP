package com.vimalesh.student_ERP.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static boolean isPast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    public static boolean isOverdue(LocalDate dueDate) {
        return dueDate.isBefore(LocalDate.now());
    }

    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static int getCurrentAcademicYear() {

        return LocalDate.now().getYear();
    }
}
