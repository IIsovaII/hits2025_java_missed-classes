package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.GantResponseDto;
import com.example.hits2025_java_missed_classes.dto.StudentsPagedListDto;
import com.example.hits2025_java_missed_classes.mapper.StudentsPagedListMapper;
import com.example.hits2025_java_missed_classes.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Students")
public class StudentsController {

    final StudentsPagedListMapper studentsPagedListMapper;
    private final StudentService studentService;

    public StudentsController(StudentsPagedListMapper studentsPagedListMapper, StudentService studentService) {
        this.studentsPagedListMapper = studentsPagedListMapper;
        this.studentService = studentService;
    }

    @Operation(summary = "Get all students (for teachers and dean workers)", description = "Get paged list of filtered students")
    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_DEANWORKER')")
    public StudentsPagedListDto getAllFilteredRequestsPaged(
            @Schema(description = "filter by specific group (by given prefix)")
            @RequestParam(required = false) String group,
            @Schema(description = "filter by specific subgroups (by any of given prefixes)")
            @RequestParam(required = false) List<String> subgroups,
            @Schema(description = "filter by teacher's favorite groups (for teachers)")
            @RequestParam(required = false) Boolean areFavoriteGroupsOnly,
            @Schema(description = "filter by student's surname")
            @RequestParam(required = false) String studentSurname,
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        return studentsPagedListMapper.toDto(
                studentService.getPagedStudentsFiltered(
                        group,
                        subgroups,
                        areFavoriteGroupsOnly,
                        studentSurname,
                        pageable)
        );
    }

    /*
    @Operation(summary = "Get all students (for teachers and dean workers)", description = "Get paged list of filtered students")
    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_DEANWORKER')")
    public GantResponseDto GetGantResponse(
            @Schema(description = "filter by specific group (by given prefix)")
            @RequestParam(required = false) String group,
            @Schema(description = "filter by specific subgroups (by any of given prefixes)")
            @RequestParam(required = false) List<String> subgroups,
            @Schema(description = "filter by teacher's favorite groups (for teachers)")
            @RequestParam(required = false) Boolean areFavoriteGroupsOnly,
            @Schema(description = "filter by student's surname")
            @RequestParam(required = false) String studentSurname,
            @Schema(description = "filters requests with start date greater than this parameter")
            @RequestParam(required = false) LocalDateTime startDate,
            @Schema(description = "filters requests with end date lesser than this parameter")
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        var sortBy = Sort.by(Sort.Direction.ASC, "groupName");
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sortBy);


        return studentsPagedListMapper.toDto(
                studentService.getPagedStudentsFiltered(
                        group,
                        subgroups,
                        areFavoriteGroupsOnly,
                        studentSurname,
                        pageable)
        );
    }*/
}