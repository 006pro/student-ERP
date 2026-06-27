package com.vimalesh.student_ERP.Util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceCalculatorTest {

    @Test
    void calculatePercentage_zeroTotalDays_returnsZero() {
        assertEquals(0.0, AttendanceCalculator.calculatePercentage(0, 0));
    }

    @Test
    void calculatePercentage_normal_returnsCorrectPercent() {
        assertEquals(80.0, AttendanceCalculator.calculatePercentage(8, 10));
    }

    @Test
    void calculatePercentage_fullAttendance_returns100() {
        assertEquals(100.0, AttendanceCalculator.calculatePercentage(20, 20));
    }

    @Test
    void calculatePercentage_zeroPresent_returnsZero() {
        assertEquals(0.0, AttendanceCalculator.calculatePercentage(0, 10));
    }

    @Test
    void calculatePercentage_roundsToTwoDecimalPlaces() {
        assertEquals(66.67, AttendanceCalculator.calculatePercentage(2, 3));
    }

    @Test
    void isBelowThreshold_percentageBelowThreshold_returnsTrue() {
        assertTrue(AttendanceCalculator.isBelowThreshold(60.0, 75.0));
    }

    @Test
    void isBelowThreshold_percentageAboveThreshold_returnsFalse() {
        assertFalse(AttendanceCalculator.isBelowThreshold(80.0, 75.0));
    }

    @Test
    void isBelowThreshold_percentageEqualToThreshold_returnsFalse() {
        assertFalse(AttendanceCalculator.isBelowThreshold(75.0, 75.0));
    }
}
