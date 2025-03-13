package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.GantGroupItemDto;
import com.example.hits2025_java_missed_classes.dto.GantMissRequestItemDto;
import com.example.hits2025_java_missed_classes.dto.GantResponseDto;
import com.example.hits2025_java_missed_classes.dto.GantStudentItemDto;
import com.example.hits2025_java_missed_classes.model.MissRequest;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.MissRequestsRepository;
import com.example.hits2025_java_missed_classes.repository.specifications.MissRequestsSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class GantMapper {
    final StudentMapper studentMapper;
    final PaginationMapper paginationMapper;
    private final MissRequestsRepository missRequestsRepository;

    public GantMapper(StudentMapper studentMapper, PaginationMapper paginationMapper, MissRequestsRepository missRequestsRepository) {
        this.studentMapper = studentMapper;
        this.paginationMapper = paginationMapper;
        this.missRequestsRepository = missRequestsRepository;
    }

    public GantResponseDto toDto(Page<User> sortedByGroupsPage, LocalDate startDate, LocalDate endDate) {
        List<User> domainStudents = sortedByGroupsPage.getContent();

        GantResponseDto finalGantResponse = new GantResponseDto(
                new ArrayList<>(),
                paginationMapper.toDto(sortedByGroupsPage)
        );

        if (domainStudents.isEmpty()) {
            return finalGantResponse;
        }

        var datesSpecification = MissRequestsSpecifications.hasMissRequestsInSegment(startDate, endDate);

        //
        GantGroupItemDto currentGantGroupItem = new GantGroupItemDto(
                domainStudents.getFirst().getGroupName(),
                List.of(
                        new GantStudentItemDto(
                                domainStudents.getFirst().getSurname(),
                                domainStudents.getFirst().getName(),
                                domainStudents.getFirst().getPatronymic(),
                                toGantDto(missRequestsRepository.findAll(datesSpecification.and(MissRequestsSpecifications.hasCreatorById(domainStudents.getFirst().getId()))).stream()
                                        .sorted(Comparator.comparing(MissRequest::getStartDate))
                                        .collect(Collectors.toList()))
                        )
                )
        );
        finalGantResponse.getGroups().add(currentGantGroupItem);
        //

        for (int i = 1; i < domainStudents.size(); i++) {
            var prevUser = domainStudents.get(i - 1);
            var currentUser = domainStudents.get(i);

            var prevUserGroupName = prevUser.getGroupName();
            var currUserGroupName = currentUser.getGroupName();

            if (!Objects.equals(prevUserGroupName, currUserGroupName)) {
                currentGantGroupItem = new GantGroupItemDto(
                        currUserGroupName,
                        new ArrayList<>()
                );
                finalGantResponse.getGroups().add(currentGantGroupItem);
            }

            Specification<MissRequest> specification = MissRequestsSpecifications.hasCreatorById(currentUser.getId());
            var requests = missRequestsRepository.findAll(datesSpecification.and(specification));

            var newGantStudentItem = new GantStudentItemDto(
                    currentUser.getSurname(),
                    currentUser.getName(),
                    currentUser.getPatronymic(),
                    toGantDto(requests.stream()
                            .sorted(Comparator.comparing(MissRequest::getStartDate))
                            .collect(Collectors.toList()))
            );
            currentGantGroupItem.getStudents().add(newGantStudentItem);
        }

        return finalGantResponse;
    }

    private GantMissRequestItemDto toGantDto(MissRequest model) {
        return new GantMissRequestItemDto(
                model.getStartDate(),
                model.getEndDate(),
                model.getType()
        );
    }

    private List<GantMissRequestItemDto> toGantDto(List<MissRequest> models) {
        return models.stream().map(this::toGantDto).toList();
    }
}