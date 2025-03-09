package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.*;
import com.example.hits2025_java_missed_classes.mapper.ConfirmationFileMapper;
import com.example.hits2025_java_missed_classes.mapper.MissRequestCreateModelMapper;
import com.example.hits2025_java_missed_classes.mapper.MissRequestEditModelMapper;
import com.example.hits2025_java_missed_classes.mapper.MissRequestPagedListMapper;
import com.example.hits2025_java_missed_classes.service.MissRequestsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/requests")
@Tag(name = "Requests")
public class MissRequestsController {

    private final MissRequestCreateModelMapper missRequestCreateModelMapper;
    private final MissRequestsService missRequestsService;
    private final MissRequestPagedListMapper missRequestPagedListMapper;
    private final MissRequestEditModelMapper missRequestEditModelMapper;
    private final ConfirmationFileMapper confirmationFileMapper;

    public MissRequestsController(MissRequestsService missRequestsService, MissRequestPagedListMapper missRequestPagedListMapper, MissRequestEditModelMapper missRequestEditModelMapper, ConfirmationFileMapper confirmationFileMapper, MissRequestCreateModelMapper missRequestCreateModelMapper) {
        this.missRequestsService = missRequestsService;
        this.missRequestPagedListMapper = missRequestPagedListMapper;
        this.missRequestEditModelMapper = missRequestEditModelMapper;
        this.confirmationFileMapper = confirmationFileMapper;
        this.missRequestCreateModelMapper = missRequestCreateModelMapper;
    }

    @Operation(summary = "Get all requests (for teachers and dean workers)", description = "Get paged list of filtered requests")
    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_DEANWORKER')")
    public MissRequestPagedListDto getAllFilteredRequestsPaged(
            @Schema(description = "filter by specific group (by given prefix)")
            @RequestParam(required = false) String group,
            @Schema(description = "filter by specific subgroups (by any of given prefixes)")
            @RequestParam(required = false) List<String> subgroups,
            @Schema(description = "filter by student's surname")
            @RequestParam(required = false) String studentSurname,
            @Schema(description = "filters requests with start date greater than this parameter")
            @RequestParam(required = false) LocalDateTime startDate,
            @Schema(description = "filters requests with end date lesser than this parameter")
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        return missRequestPagedListMapper.toDto(
                missRequestsService.getPagedMissRequestFiltered(
                        group,
                        subgroups,
                        studentSurname,
                        startDate,
                        endDate,
                        pageable)
        );
    }

    @Operation(summary = "Create new request (for students)")
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public UUID createMissRequest(@RequestBody MissRequestCreateModelDto model) {
        return missRequestsService.add(missRequestCreateModelMapper.toDomain(model)).getId();
    }

    @Operation(summary = "Edit request (for dean workers)")
    @PutMapping("/{id}/edit")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public UUID editMissRequest(@PathVariable UUID id, @RequestBody MissRequestEditModelDto model) {
        return missRequestsService.edit(id, missRequestEditModelMapper.toDomain(model)).getId();
    }

    @Operation(summary = "Prolong existing request (for students)", description = "If request status is DENIED, than it does nothing, otherwise it prolongs request and also sets status to IN_QUEUE")
    @PutMapping("/{id}/prolong")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public UUID prolongMissRequest(@PathVariable UUID id, @RequestBody MissRequestProlongModelDto model) {
        return missRequestsService.prolong(id, model.getNewEndDate()).getId();
    }

    @Operation(summary = "Get list of user requests (for students)")
    @GetMapping("/my")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public MissRequestPagedListDto getMyRequestsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return missRequestPagedListMapper.toDto(
                missRequestsService.getMyRequestPaged(pageable)
        );
    }

    @Operation(summary = "Attach confirmation documents to the request by it's id (for students and dean workers)")
    @PostMapping("/{id}/confirmation")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_DEANWORKER')")
    public UUID addConfirmation(
            @PathVariable UUID id,
            @RequestBody List<ConfirmationFileDto> model) {
        return missRequestsService.attachConfirmation(
                id, model.stream().map(confirmationFileMapper::toDomain).toList()).getId();
    }
}