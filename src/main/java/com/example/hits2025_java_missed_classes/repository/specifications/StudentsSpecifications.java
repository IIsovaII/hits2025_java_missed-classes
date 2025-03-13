package com.example.hits2025_java_missed_classes.repository.specifications;

import com.example.hits2025_java_missed_classes.model.*;
import jakarta.persistence.FetchType;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsSpecifications {
    public static Specification<User> isInGroupByName(String groupName) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Group> groupsJoin = root.join("group");
            return criteriaBuilder.like(groupsJoin.get("name"), groupName + "%");
        };
    }

    public static Specification<User> isInAnyOfSubgroupsByName(List<String> subgroupNames) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Subgroup> subgroupsJoin = root.join("subgroups");

            Predicate[] predicates = new Predicate[subgroupNames.size()];

            for (int i = 0; i < subgroupNames.size(); i++) {
                predicates[i] = criteriaBuilder.like(subgroupsJoin.get("name"), subgroupNames.get(i) + "%");
            }

            return criteriaBuilder.or(predicates);
        };
    }

    public static Specification<User> hasSurname(String surname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("surname"), surname + "%");
    }

    public static Specification<User> isInTeacherFavorites(User teacher) {
        return isInAnyOfSubgroupsByName(teacher.getFavoriteSubgroups().stream().map(Subgroup::getName).collect(Collectors.toList()));
    }

    public static Specification<User> hasMissRequestsInSegment(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {return Specification.where(null);}

        if (endDate == null) {
            return hasEndDateGreaterThanOrEqualTo(startDate);
        }

        if (startDate == null) {
            return hasStartDateLesserThanOrEqualTo(endDate);
        }

        return (root, query, criteriaBuilder) -> {
            Join<User, MissRequest> missRequestJoin = root.join("createdMissRequests");
            return criteriaBuilder.and(
                    criteriaBuilder.lessThanOrEqualTo(missRequestJoin.get("startDate"), endDate),
                    criteriaBuilder.greaterThanOrEqualTo(missRequestJoin.get("endDate"), startDate)
            );
        };
    }

    private static Specification<User> hasEndDateGreaterThanOrEqualTo(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> {
            Join<User, MissRequest> missRequestJoin = root.join("createdMissRequests");
            return criteriaBuilder.greaterThanOrEqualTo(missRequestJoin.get("endDate"), startDate);
        };
    }

    private static Specification<User> hasStartDateLesserThanOrEqualTo(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            Join<User, MissRequest> missRequestJoin = root.join("createdMissRequests");
            return criteriaBuilder.lessThanOrEqualTo(missRequestJoin.get("startDate"), endDate);
        };
    }
}