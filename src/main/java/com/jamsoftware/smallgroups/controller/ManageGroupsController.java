package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.model.CustomUserDetails;
import com.jamsoftware.smallgroups.service.GroupService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/groups/manage")
public class ManageGroupsController {
    GroupService groupService;

    public ManageGroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping({"", "/"})
    public String getDirectory(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getUserId();
        model.addAttribute("groups", groupService.findAllByLeaderId());
        return "manage-groups";
    }

    @GetMapping("/addGroup")
    public String addGroup(Model model) {
        return "add-edit-group";
    }

    @PostMapping("/addGroup")
    public String addGroupSubmit(Model model) {
        return "add-edit-group";
    }
}
