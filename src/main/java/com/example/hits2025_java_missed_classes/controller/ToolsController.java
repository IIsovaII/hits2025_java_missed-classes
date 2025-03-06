package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.service.ToolsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tools")
@Tag(name = "Tools")
public class ToolsController {

    @Autowired
    private ToolsService toolsService;
    @Autowired
    private GroupMapper groupMapper;

//    @PostMapping("/group/add")
//    public GroupDTO addGroup(@RequestBody GroupDTO group) {
////        return toolsService.addGroup(groupMapper.toDTO(group));
//    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        toolsService.deleteGroupById(id);
    }
}
