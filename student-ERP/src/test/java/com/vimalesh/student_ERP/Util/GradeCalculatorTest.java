package com.vimalesh.student_ERP.Util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorTest {

    @Test
    void calculateGrade_ninetyPercentOrAbove_returnsAPlus() {
        assertEquals("A+", GradeCalculator.calculateGrade(90, 100));
    }

    @Test
    void calculateGrade_seventyFiveToEightyNine_returnsA() {
        assertEquals("A", GradeCalculator.calculateGrade(80, 100));
    }

    @Test
    void calculateGrade_sixtyToSeventyFour_returnsB() {
        assertEquals("B", GradeCalculator.calculateGrade(65, 100));
    }

    @Test
    void calculateGrade_fortyToFiftyNine_returnsC() {
        assertEquals("C", GradeCalculator.calculateGrade(45, 100));
    }

    @Test
    void calculateGrade_belowForty_returnsF() {
        assertEquals("F", GradeCalculator.calculateGrade(30, 100));
    }

    @Test
    void calculateGrade_exactlyAtBoundary75_returnsA() {
        assertEquals("A", GradeCalculator.calculateGrade(75, 100));
    }

    @Test
    void calculateGrade_zeroMaxMarks_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> GradeCalculator.calculateGrade(50, 0));
    }

    @Test
    void calculateGrade_negativeMaxMarks_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> GradeCalculator.calculateGrade(50, -10));
    }

    @Test
    void calculatePercentage_normal_returnsCorrectValue() {
        assertEquals(80.0, GradeCalculator.calculatePercentage(80, 100));
    }

    @Test
    void calculatePercentage_zeroMaxMarks_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> GradeCalculator.calculatePercentage(50, 0));
    }

    @Test
    void calculatePercentage_fullMarks_returns100() {
        assertEquals(100.0, GradeCalculator.calculatePercentage(50, 50));
    }
}
