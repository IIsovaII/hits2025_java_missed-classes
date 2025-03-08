package com.example.hits2025_java_missed_classes.repository.specifications;

import com.example.hits2025_java_missed_classes.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

public class StudentsSpecifications {
    public static Specification<User> isInGroupByName(String groupName) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Group> groupsJoin = root.join("groups");
            return criteriaBuilder.like(groupsJoin.get("name"), groupName + "%");
        };
    }

    public static Specification<User> isInAnyOfSubgroupsByName(List<String> subgroupNames) {
        return (root, query, criteriaBuilder) -> {
            Join<User, SubGroup> subgroupsJoin = root.join("subgroups");

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
        return isInAnyOfSubgroupsByName(teacher.getFavSubgroups().stream().map(SubGroup::getName).collect(Collectors.toList()));
    }
}
