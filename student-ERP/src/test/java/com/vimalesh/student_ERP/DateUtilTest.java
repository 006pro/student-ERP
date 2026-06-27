package com.vimalesh.student_ERP;

import com.vimalesh.student_ERP.Util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void isPast_pastDate_returnsTrue() {
        Assertions.assertTrue(DateUtil.isPast(LocalDate.now().minusDays(1)));
    }

    @Test
    void isPast_futureDate_returnsFalse() {
        assertFalse(DateUtil.isPast(LocalDate.now().plusDays(1)));
    }

    @Test
    void isPast_todayDate_returnsFalse() {
        assertFalse(DateUtil.isPast(LocalDate.now()));
    }

    @Test
    void isOverdue_pastDate_returnsTrue() {
        assertTrue(DateUtil.isOverdue(LocalDate.now().minusDays(1)));
    }

    @Test
    void isOverdue_futureDate_returnsFalse() {
        assertFalse(DateUtil.isOverdue(LocalDate.now().plusDays(1)));
    }

    @Test
    void formatDate_formatsAsDDMMYYYY() {
        LocalDate date = LocalDate.of(2024, 6, 15);
        assertEquals("15-06-2024", DateUtil.formatDate(date));
    }

    @Test
    void getCurrentAcademicYear_returnsCurrentYear() {
        assertEquals(LocalDate.now().getYear(), DateUtil.getCurrentAcademicYear());
    }
}
