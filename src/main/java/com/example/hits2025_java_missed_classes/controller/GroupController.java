
package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.GroupDto;
import com.example.hits2025_java_missed_classes.dto.SubgroupDto;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import com.example.hits2025_java_missed_classes.security.JwtUtil;
import com.example.hits2025_java_missed_classes.service.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/group")
@Tag(name = "group")
public class GroupController {

    private final GroupService groupService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public GroupController(GroupService groupService, UserRepository userRepository, JwtUtil jwtUtil) {
        this.groupService = groupService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/favourite/group/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> addGroup(@RequestBody  List<GroupDto> groups, @RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String email = jwtUtil.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(email);
        groupService.addGroupToFav(user.get().getId(),groups);
        return null;
    }

    @PostMapping("/favourite/subgroup/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> addSubgroup(@RequestBody List<SubgroupDto> subgroups, @RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String email = jwtUtil.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(email);

        groupService.addSubgroupToFav(user.get().getId(), subgroups);
        return null;
    }

    @DeleteMapping("/favourite/group/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteGroupFromFav(@RequestBody String groupName, @RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String email = jwtUtil.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(email);
        groupService.deleteGroupFromFav(user.get().getId(), groupName);
    }

    @DeleteMapping("/favourite/subgroup/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteSubgroupFromFav(@RequestBody UUID subgroupId, @RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String email = jwtUtil.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(email);
        groupService.deleteSubgroupFromFav(user.get().getId(), subgroupId);
    }

    @GetMapping("/favourite")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public List<GroupDto> getFavouriteGroup(@RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String username = jwtUtil.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(username);
        return groupService.getFavGroups(user.get().getId());
    }

    @GetMapping("/favourite/searchGroup")
    public List<GroupDto> getGroupsByName(@RequestParam String groupName) {
        return groupService.getGroups(groupName);
    }
}
