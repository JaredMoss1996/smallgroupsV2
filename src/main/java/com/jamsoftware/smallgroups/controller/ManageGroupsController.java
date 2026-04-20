package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.model.Group;
import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.service.CurrentMemberService;
import com.jamsoftware.smallgroups.service.GroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/groups")
public class ManageGroupsController {
    private final GroupService groupService;
    private final CurrentMemberService currentMemberService;

    public ManageGroupsController(GroupService groupService, CurrentMemberService currentMemberService) {
        this.groupService = groupService;
        this.currentMemberService = currentMemberService;
    }

    @GetMapping({"", "/"})
    public String getDirectory(Model model) {
        Member currentMember = currentMemberService.getCurrentMember();
        model.addAttribute("groups", groupService.findAllByLeaderId(currentMember.getId()));
        return "manage-groups";
    }

    @GetMapping("/create")
    public String createGroup(Model model) {
        model.addAttribute("groupData", new Group());
        model.addAttribute("isCreate", true);
        return "create-edit-group";
    }

    @PostMapping("/create")
    public String createGroupSubmit(@ModelAttribute Group groupData, Model model) {
        return "manage-groups";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("@authz.canEditGroup(#id)")
    public String editGroup(@PathVariable Long id, Model model) {
        model.addAttribute("groupData", groupService.findById(id));
        model.addAttribute("isCreate", false);
        return "create-edit-group";
    }

    @PostMapping("/edit")
    public String editGroupSubmit(@ModelAttribute Group groupData, Model model) {
        return "manage-groups";
    }
}
