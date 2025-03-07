package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.MissRequestPagedListDto;
import com.example.hits2025_java_missed_classes.model.MissRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class MissRequestPagedListMapper {
    final MissRequestMapper missRequestMapper;
    final PaginationMapper paginationMapper;

    public MissRequestPagedListMapper(MissRequestMapper missRequestMapper, PaginationMapper paginationMapper) {
        this.missRequestMapper = missRequestMapper;
        this.paginationMapper = paginationMapper;
    }

    public MissRequestPagedListDto toDto(Page<MissRequest> domainPage) {
        return new MissRequestPagedListDto(
                paginationMapper.toDTO(domainPage),
                domainPage
                        .getContent()
                        .stream()
                        .map(missRequestMapper::toDto)
                        .toList()
        );
    }
}
