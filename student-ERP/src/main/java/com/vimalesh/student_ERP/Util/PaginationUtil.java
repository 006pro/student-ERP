package com.vimalesh.student_ERP.Util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {
    public static Pageable buildPageable(int page, int size, String sortBy) {
        return PageRequest.of(page, size, Sort.by(sortBy));
    }
}