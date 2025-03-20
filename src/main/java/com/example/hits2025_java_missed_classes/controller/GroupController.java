
package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.GetSubGroupDto;
import com.example.hits2025_java_missed_classes.dto.GroupDto;
import com.example.hits2025_java_missed_classes.dto.SubgroupDto;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import com.example.hits2025_java_missed_classes.security.JwtUtil;
import com.example.hits2025_java_missed_classes.service.GroupService;
import com.example.hits2025_java_missed_classes.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/group")
@Tag(name = "group")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @PostMapping("/favourite/{groupName}/group/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void addGroup(@PathVariable String groupName) {
        User user = userService.getCurrentUser();
        groupService.addGroupToFav(user.getId(), groupName);
    }

    @PostMapping("/favourite/{subgroupId}/subgroup/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void addSubgroup(@PathVariable UUID subgroupId) {
        User user = userService.getCurrentUser();
        groupService.addSubgroupToFav(user.getId(), subgroupId);
    }

    @DeleteMapping("/favourite/{groupName}/group/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteGroupFromFav(@PathVariable String groupName) {
        User user = userService.getCurrentUser();
        groupService.deleteGroupFromFav(user.getId(), groupName);
    }

    @DeleteMapping("/favourite/{subgroupId}/subgroup/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteSubgroupFromFav(@PathVariable UUID subgroupId) {
        User user = userService.getCurrentUser();
        groupService.deleteSubgroupFromFav(user.getId(), subgroupId);
    }

    @GetMapping("/favourite")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public List<GroupDto> getFavouriteGroup() {
        User user = userService.getCurrentUser();
        return groupService.getFavGroups(user.getId());
    }

    @GetMapping("/subgroupIdGet")
    public UUID getSubgroupGet(@RequestParam GetSubGroupDto subGroupDto) {
        return groupService.getSubgroup(subGroupDto);
    }

    @GetMapping("/searchGroup")
    public List<GroupDto> getGroupsByName(@RequestParam String groupName) {
        return groupService.getGroups(groupName);
    }
}
