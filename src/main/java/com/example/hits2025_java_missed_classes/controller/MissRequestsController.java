package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.*;
import com.example.hits2025_java_missed_classes.mapper.*;
import com.example.hits2025_java_missed_classes.model.ArchiveModel;
import com.example.hits2025_java_missed_classes.repository.ConfirmationFileRepository;
import com.example.hits2025_java_missed_classes.service.ConfirmationFileService;
import com.example.hits2025_java_missed_classes.service.MissRequestsService;
import com.example.hits2025_java_missed_classes.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
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
    private final ConfirmationFileCreateModelMapper confirmationFileMapper;
    private final ConfirmationFileService confirmationFileService;
    private final GantMapper gantMapper;
    private final StudentService studentService;

    public MissRequestsController(MissRequestsService missRequestsService, MissRequestPagedListMapper missRequestPagedListMapper, MissRequestEditModelMapper missRequestEditModelMapper, ConfirmationFileCreateModelMapper confirmationFileMapper, MissRequestCreateModelMapper missRequestCreateModelMapper, ConfirmationFileRepository confirmationFileRepository, ConfirmationFileService confirmationFileService, GantMapper gantMapper, StudentService studentService) {
        this.missRequestsService = missRequestsService;
        this.missRequestPagedListMapper = missRequestPagedListMapper;
        this.missRequestEditModelMapper = missRequestEditModelMapper;
        this.confirmationFileMapper = confirmationFileMapper;
        this.missRequestCreateModelMapper = missRequestCreateModelMapper;
        this.confirmationFileService = confirmationFileService;
        this.gantMapper = gantMapper;
        this.studentService = studentService;
    }

    @Operation(summary = "Get all requests (for teachers and dean workers)", description = "Get paged list of filtered requests, ASC sorted by endDate")
    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_DEAN_WORKER')")
    public MissRequestPagedListDto getAllFilteredRequestsPaged(
            @Schema(description = "filter by specific group (by given prefix)")
            @RequestParam(required = false) String group,
            @Schema(description = "filter by specific subgroups (by any of given prefixes)")
            @RequestParam(required = false) List<String> subgroups,
            @Schema(description = "filter by student's surname")
            @RequestParam(required = false) String studentSurname,
            @Schema(description = "filters requests with start date greater than this parameter")
            @RequestParam(required = false) LocalDate startDate,
            @Schema(description = "filters requests with end date lesser than this parameter")
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        Sort sort = Sort.by(Sort.Direction.ASC, "endDate");
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

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
    public UUID createMissRequest(@Valid @RequestBody MissRequestCreateModelDto model) {
        return missRequestsService.add(missRequestCreateModelMapper.toDomain(model)).getId();
    }

    @Operation(summary = "Edit request (for dean workers)")
    @PutMapping("{id}/edit")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public UUID editMissRequest(@PathVariable UUID id, @Valid @RequestBody MissRequestEditModelDto model) {
        return missRequestsService.edit(id, missRequestEditModelMapper.toDomain(model)).getId();
    }

    @Operation(summary = "Prolong existing request (for students)", description = "If request status is DENIED, than it does nothing, otherwise it prolongs request and also sets status to IN_QUEUE")
    @PutMapping("{id}/prolong")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public UUID prolongMissRequest(@PathVariable UUID id, @Valid @RequestBody MissRequestProlongModelDto model) {
        return missRequestsService.prolong(id, model.getNewEndDate()).getId();
    }

    @Operation(summary = "Get list of user requests (for students)")
    @GetMapping("my")
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
    @PostMapping("{id}/confirmation")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_DEAN_WORKER')")
    public UUID addConfirmation(
            @PathVariable UUID id,
            @RequestBody List<ConfirmationFileCreateModelDto> model) {
        return missRequestsService.attachConfirmation(
                id, model.stream().map(confirmationFileMapper::toDomain).toList()).getId();
    }

    @Operation(summary = "Export confirmation document by it's id (for dean workers)")
    @GetMapping("confirmation/{id}/export")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public ResponseEntity<ByteArrayResource> exportArchivedAttachmentById(@PathVariable UUID id) {
        ArchiveModel archive = confirmationFileService.exportArchivedAttachmentsById(id);
        ByteArrayResource resource = new ByteArrayResource(archive.getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + archive.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(archive.getData().length)
                .body(resource);
    }

    @Operation(summary = "Export confirmation documents of the requests by theirs(requests) ids (for dean workers)")
    @GetMapping("confirmations/export")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public ResponseEntity<ByteArrayResource> exportArchivedAttachmentsByRequestsIds(@RequestBody List<UUID> ids) {
        ArchiveModel archive = confirmationFileService.exportArchivedAttachmentsByRequestsIds(ids);
        ByteArrayResource resource = new ByteArrayResource(archive.getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + archive.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(archive.getData().length)
                .body(resource);
    }

    @Operation(summary = "Export students misses in csv format (for teachers)")
    @GetMapping("export/csv")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<ByteArrayResource> exportMissesTable(
            @Schema(description = "filter by specific group (by given prefix)")
            @RequestParam(required = false) String group,
            @Schema(description = "filter by specific subgroups (by any of given prefixes)")
            @RequestParam(required = false) List<String> subgroups,
            @Schema(description = "filter by teacher's favorite groups (for teachers)")
            @RequestParam(required = false, defaultValue = "false") boolean areFavoriteGroupsOnly,
            @Schema(description = "filter by student's surname")
            @RequestParam(required = false) String studentSurname,
            @Schema(description = "filters requests with start date greater than this parameter")
            @RequestParam(required = false) LocalDate startDate,
            @Schema(description = "filters requests with end date lesser than this parameter")
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        var sortBy = Sort.by(Sort.Direction.ASC, "groupName");
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sortBy);

        GantResponseDto gantResponse = gantMapper.toDto(
                studentService.getPagedStudentsFiltered(
                        group,
                        subgroups,
                        areFavoriteGroupsOnly,
                        studentSurname,
                        startDate,
                        endDate,
                        pageable)
        );

        byte[] csvBytes = missRequestsService.generateMissesCsv(gantResponse);
        ByteArrayResource resource = new ByteArrayResource(csvBytes);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "Отчетность" + LocalDateTime.now() + ".csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }
}