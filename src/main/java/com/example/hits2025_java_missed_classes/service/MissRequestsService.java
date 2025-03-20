package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.GantGroupItemDto;
import com.example.hits2025_java_missed_classes.dto.GantMissRequestItemDto;
import com.example.hits2025_java_missed_classes.dto.GantResponseDto;
import com.example.hits2025_java_missed_classes.dto.GantStudentItemDto;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.BadRequestException;
import com.example.hits2025_java_missed_classes.exception.forbidden.RequestDeniedException;
import com.example.hits2025_java_missed_classes.mapper.MissRequestTypeMapper;
import com.example.hits2025_java_missed_classes.model.*;
import com.example.hits2025_java_missed_classes.repository.ConfirmationFileRepository;
import com.example.hits2025_java_missed_classes.repository.MissRequestsRepository;
import com.example.hits2025_java_missed_classes.repository.specifications.MissRequestsSpecifications;
import com.opencsv.CSVWriter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MissRequestsService {
    final MissRequestsRepository repository;
    final ConfirmationFileRepository confirmationFileRepository;
    private final UserService userService;
    private final MissRequestTypeMapper missRequestTypeMapper;

    public MissRequestsService(MissRequestsRepository repository, UserService userService, ConfirmationFileRepository confirmationFileRepository, MissRequestTypeMapper missRequestTypeMapper) {
        this.repository = repository;
        this.confirmationFileRepository = confirmationFileRepository;
        this.userService = userService;
        this.missRequestTypeMapper = missRequestTypeMapper;
    }

    public MissRequest add(MissRequest missRequest) {
        return repository.save(missRequest);
    }

    public MissRequest add(MissRequestCreateModel model) {
        MissRequest missRequest = new MissRequest();

        missRequest.setStartDate(model.getStartDate());
        missRequest.setEndDate(model.getEndDate());

        model.getConfirmationFiles().forEach(confirmationFile ->
            confirmationFile.setMissRequest(missRequest));

        missRequest.setConfirmationFiles(model.getConfirmationFiles());
        missRequest.setType(model.getType());
        missRequest.setCreator(userService.getCurrentUser());

        return repository.save(missRequest);
    }

    public MissRequest edit(UUID id, MissRequestEditModel editModel) {
        MissRequest missRequest = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Miss request not found with id: " + id));

        missRequest.setStartDate(editModel.getStartDate());
        missRequest.setEndDate(editModel.getEndDate());
        missRequest.setType(editModel.getType());
        missRequest.setStatusSetBy(userService.getCurrentUser());
        missRequest.setStatus(editModel.getStatus());

        return repository.save(missRequest);
    }

    @Transactional
    public MissRequest attachConfirmation(UUID id, List<ConfirmationFile> files) {
        MissRequest missRequest = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Miss request not found with id: " + id));

        files.forEach(confirmationFile ->
                confirmationFile.setMissRequest(missRequest));

        return repository.save(missRequest);
    }

    public MissRequest prolong(UUID id, LocalDate newEndDate) {
        MissRequest missRequest = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Miss request not found with id: " + id));

        if (newEndDate.isBefore(missRequest.getEndDate())) {
            //TODO throw different exception
            throw new BadRequestException("New endDate cannot be lesser than old endDate");
        }

        if (missRequest.getStatus() == MissRequestStatus.DENIED) {
            throw new RequestDeniedException("Cannot prolong request with status DENIED");
        }

        missRequest.setEndDate(newEndDate);
        missRequest.setStatus(MissRequestStatus.IN_QUEUE);

        return repository.save(missRequest);
    }

    public Page<MissRequest> getMyRequestPaged(Pageable pageable) {
        Specification<MissRequest> specification =
                Specification.where(MissRequestsSpecifications.hasCreatorById(
                        userService.getCurrentUser().getId()
                ));
        return repository.findAll(specification, pageable);
    }

    public Page<MissRequest> getPagedMissRequestFiltered(
            String groupName,
            List<String> subgroups,
            String studentSurname,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {

        //TODO throw different exception
        if (endDate != null && startDate != null &&
                endDate.isBefore(startDate)) {
            throw new BadRequestException("EndDate cannot be before startDate");
        }

        Specification<MissRequest> specification = Specification.where(null);

        if (groupName != null) {
            specification = specification.and(MissRequestsSpecifications.madeByStudentFromGroupByName(groupName));
        }
        if (subgroups != null) {
            specification = specification.and(MissRequestsSpecifications.madeByStudentFromSubgroupByName(subgroups));
        }
        if (studentSurname != null) {
            specification = specification.and(MissRequestsSpecifications.madeByStudentBySurname(studentSurname));
        }
        if (startDate != null || endDate != null) {
            specification = specification.and(MissRequestsSpecifications.hasMissRequestsInSegment(startDate, endDate));
        }

        return repository.findAll(specification, pageable);
    }

    //TODO убейте меня за этот код и того, кто придумал экспортировать csv
    public byte[] generateMissesCsv(GantResponseDto gantResponse){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        CSVWriter csvWriter = new CSVWriter(writer);

        LocalDate startDate = gantResponse
                .getGroups().getFirst()
                .getStudents().getFirst()
                .getRequests().getFirst()
                .getStartDate();
        LocalDate endDate = gantResponse
                .getGroups().getFirst()
                .getStudents().getFirst()
                .getRequests().getLast()
                .getEndDate();

        for (GantGroupItemDto group: gantResponse.getGroups()){
            for (GantStudentItemDto student: group.getStudents()){
                LocalDate studentFirstMissDate = student.getRequests().getFirst().getStartDate();
                LocalDate studentLastMissDate = student.getRequests().getLast().getEndDate();

                if (startDate.isAfter(studentFirstMissDate))
                    startDate = studentFirstMissDate;

                if (endDate.isBefore(studentLastMissDate))
                    endDate = studentLastMissDate;
            }
        }

        int totalDays = (int)ChronoUnit.DAYS.between(startDate, endDate);

        for (GantGroupItemDto group: gantResponse.getGroups()) {
            csvWriter.writeNext(new String[]{group.getGroupName()});

            for (GantStudentItemDto student : group.getStudents()) {
                var nextLine = new String[totalDays + 1];
                Arrays.fill(nextLine, "");
                nextLine[0] = student.getName();

                for (GantMissRequestItemDto request: student.getRequests()) {
                    int startDiff = (int)ChronoUnit.DAYS.between(startDate, request.getStartDate());
                    int endDiff = (int)ChronoUnit.DAYS.between(startDate, request.getEndDate());
                    char typeChar = missRequestTypeMapper.toRuCharacter(request.getType());

                    for (int i = startDiff; i < endDiff; i++) {
                        nextLine[i + 1] = String.valueOf(typeChar);
                    }
                }

                csvWriter.writeNext(nextLine);
            }
        }

        try {
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outputStream.toByteArray();
    }
}