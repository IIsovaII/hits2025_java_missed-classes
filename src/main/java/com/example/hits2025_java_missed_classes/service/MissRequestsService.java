package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.exception.RequestDeniedException;
import com.example.hits2025_java_missed_classes.model.*;
import com.example.hits2025_java_missed_classes.repository.MissRequestsRepository;
import com.example.hits2025_java_missed_classes.repository.specifications.MissRequestsSpecifications;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MissRequestsService {
    final MissRequestsRepository repository;
    private final UserService userService;

    public MissRequestsService(MissRequestsRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public MissRequest add(MissRequest missRequest) {
        return repository.save(missRequest);
    }

    public MissRequest add(MissRequestCreateModel model) {
        MissRequest missRequest = new MissRequest();

        missRequest.setStartDate(model.getStartDate());
        missRequest.setEndDate(model.getEndDate());

        //TODO не уверен в этом моменте
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
        missRequest.setStatus(missRequest.getStatus());

        return repository.save(missRequest);
    }

    public MissRequest attachConfirmation(UUID id, List<ConfirmationFile> files) {
        MissRequest missRequest = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Miss request not found with id: " + id));

        missRequest.getConfirmationFiles().addAll(files);

        return repository.save(missRequest);
    }

    public MissRequest prolong(UUID id, LocalDateTime newEndDate) {
        MissRequest missRequest = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Miss request not found with id: " + id));

        if (newEndDate.isBefore(missRequest.getEndDate())) {
            throw new IllegalArgumentException("New endDate cannot be lesser than old endDate");
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
            List<String> subGroups,
            String studentSurname,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {

        if (endDate != null && startDate != null &&
                endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("EndDate cannot be before startDate");
        }

        Specification<MissRequest> specification = Specification.where(null);

        if (groupName != null) {
            specification = specification.and(MissRequestsSpecifications.madeByStudentFromGroupByName(groupName));
        }
        if (subGroups != null) {
            specification = specification.and(MissRequestsSpecifications.madeByStudentFromSubgroupByName(subGroups));
        }
        if (studentSurname != null) {
            specification = specification.and(MissRequestsSpecifications.madeByStudentBySurname(studentSurname));
        }
        if (startDate != null) {
            specification = specification.and(MissRequestsSpecifications.hasStartDateGreaterThan(startDate));
        }
        if (endDate != null) {
            specification = specification.and(MissRequestsSpecifications.hasEndDateLesserThan(endDate));
        }

        return repository.findAll(specification, pageable);
    }
}