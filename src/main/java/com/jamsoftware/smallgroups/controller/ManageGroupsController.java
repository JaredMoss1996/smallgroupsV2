package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.service.CurrentMemberService;
import com.jamsoftware.smallgroups.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/groups/manage")
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

    @GetMapping("/addGroup")
    public String addGroup(Model model) {
        return "add-edit-group";
    }

    @PostMapping("/addGroup")
    public String addGroupSubmit(Model model) {
        return "add-edit-group";
    }
}
