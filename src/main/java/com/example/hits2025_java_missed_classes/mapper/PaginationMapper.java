package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationMapper {
    public <T> Pagination toDto(Page<T> domainPage) {
        return new Pagination(
                domainPage.getNumber(),
                domainPage.getSize(),
                domainPage.getTotalPages()
        );
    }
}