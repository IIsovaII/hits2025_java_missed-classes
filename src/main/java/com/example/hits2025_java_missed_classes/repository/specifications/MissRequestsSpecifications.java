package com.example.hits2025_java_missed_classes.repository.specifications;

import com.example.hits2025_java_missed_classes.model.MissRequest;
import com.example.hits2025_java_missed_classes.model.SubGroup;
import com.example.hits2025_java_missed_classes.model.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MissRequestsSpecifications {
    public static Specification<MissRequest> madeByStudentFromGroupByName(String groupName) {
        return (root, query, criteriaBuilder) -> {
            Join<MissRequest, User> creatorJoin = root.join("creator");
            return criteriaBuilder.like(creatorJoin.get("groupName"), groupName + "%");
        };
    }

    public static Specification<MissRequest> madeByStudentBySurname(String surname) {
        return (root, query, criteriaBuilder) -> {
            Join<MissRequest, User> creatorJoin = root.join("creator");
            return criteriaBuilder.like(creatorJoin.get("surname"), surname + "%");
        };
    }

    public static Specification<MissRequest> madeByStudentFromSubgroupByName(List<String> subgroupNames) {
        return (root, query, criteriaBuilder) -> {
            Join<MissRequest, User> creatorJoin = root.join("creator");
            Join<User, SubGroup> subgroupsJoin = creatorJoin.join("subgroups");

            Predicate[] predicates = new Predicate[subgroupNames.size()];

            for (int i = 0; i < subgroupNames.size(); i++) {
                predicates[i] = criteriaBuilder.like(subgroupsJoin.get("name"), subgroupNames.get(i) + "%");
            }

            return criteriaBuilder.or(predicates);
        };
    }

    public static Specification<MissRequest> hasStartDateGreaterThan(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("startDate"), startDate);
    }

    public static Specification<MissRequest> hasEndDateLesserThan(LocalDateTime endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("endDate"), endDate);
    }

    public static Specification<MissRequest> hasEndDateGreaterThanOrEqualTo(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), startDate);
    }

    public static Specification<MissRequest> hasStartDateLesserThanOrEqualTo(LocalDateTime endDate) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), endDate);
    }

    public static Specification<MissRequest> hasCreatorById(UUID id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("creator").get("id"), id);
    }
}