package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import com.example.hits2025_java_missed_classes.repository.specifications.StudentsSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {
    private final UserService userService;
    private final UserRepository userRepository;

    public StudentService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public Page<User> getPagedStudentsFiltered(
            String groupName,
            List<String> subgroups,
            Boolean areFavoriteGroupsOnly,
            String studentSurname,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {

        User currentUser = userService.getCurrentUser();
        Specification<User> specification = Specification.where(null);

        if (currentUser.getRoles().contains(Role.ROLE_TEACHER) && areFavoriteGroupsOnly) {
            specification = specification.and(StudentsSpecifications.isInTeacherFavorites(currentUser));
        }
        if (groupName != null) {
            specification = specification.and(StudentsSpecifications.isInGroupByName(groupName));
        }
        if (subgroups != null) {
            specification = specification.and(StudentsSpecifications.isInAnyOfSubgroupsByName(subgroups));
        }
        if (studentSurname != null) {
            specification = specification.and(StudentsSpecifications.hasSurname(studentSurname));
        }
        if (startDate != null || endDate != null) {
            specification = specification.and(StudentsSpecifications.hasMissRequestsInSegment(startDate, endDate));
        }

        return userRepository.findAll(specification, pageable);
    }
}
