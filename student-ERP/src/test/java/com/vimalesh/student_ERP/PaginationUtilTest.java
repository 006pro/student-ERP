package com.vimalesh.student_ERP;

import com.vimalesh.student_ERP.Util.PaginationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

class PaginationUtilTest {

    @Test
    void buildPageable_returnsCorrectPage() {
        Pageable pageable = PaginationUtil.buildPageable(0, 10, "name");
        assertEquals(0, pageable.getPageNumber());
    }

    @Test
    void buildPageable_returnsCorrectSize() {
        Pageable pageable = PaginationUtil.buildPageable(0, 10, "name");
        assertEquals(10, pageable.getPageSize());
    }

    @Test
    void buildPageable_returnsCorrectSort() {
        Pageable pageable = PaginationUtil.buildPageable(0, 10, "name");
        assertEquals(Sort.by("name"), pageable.getSort());
    }

    @Test
    void buildPageable_secondPage_returnsPageNumberTwo() {
        Pageable pageable = PaginationUtil.buildPageable(2, 5, "id");
        assertEquals(2, pageable.getPageNumber());
        assertEquals(5, pageable.getPageSize());
    }
}
