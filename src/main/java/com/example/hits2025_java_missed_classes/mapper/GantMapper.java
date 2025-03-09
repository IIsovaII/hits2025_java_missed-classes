package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.GantGroupItemDto;
import com.example.hits2025_java_missed_classes.dto.GantMissRequestItemDto;
import com.example.hits2025_java_missed_classes.dto.GantResponseDto;
import com.example.hits2025_java_missed_classes.dto.GantStudentItemDto;
import com.example.hits2025_java_missed_classes.model.MissRequest;
import com.example.hits2025_java_missed_classes.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class GantMapper {
    final StudentMapper studentMapper;
    final PaginationMapper paginationMapper;

    public GantMapper(StudentMapper studentMapper, PaginationMapper paginationMapper) {
        this.studentMapper = studentMapper;
        this.paginationMapper = paginationMapper;
    }

    public GantResponseDto toDto(Page<User> sortedByGroupsPage) {
        List<User> domainStudents = sortedByGroupsPage.getContent();

        GantResponseDto finalGantResponse = new GantResponseDto(
                new ArrayList<>(),
                paginationMapper.toDTO(sortedByGroupsPage)
        );

        if (domainStudents.isEmpty()) {
            return finalGantResponse;
        }

        GantGroupItemDto currentGantGroupItem = new GantGroupItemDto(
                domainStudents.getFirst().getGroupName(),
                new ArrayList<>()
        );
        finalGantResponse.getGroups().add(currentGantGroupItem);

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



            var newGantStudentItem = new GantStudentItemDto(
                    currentUser.getSurname(),
                    currentUser.getUsername(),
                    currentUser.getPatronymic(),
                    toGantDto(currentUser.getCreatedMissRequests()) // TODO might be optimized by not fetching whole entity
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