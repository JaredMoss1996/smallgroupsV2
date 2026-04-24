package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.service.CurrentMemberService;
import com.jamsoftware.smallgroups.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/joined")
public class JoinedGroupsController {
    private final GroupService groupService;
    private final CurrentMemberService currentMemberService;

    public JoinedGroupsController(GroupService groupService, CurrentMemberService currentMemberService) {
        this.groupService = groupService;
        this.currentMemberService = currentMemberService;
    }

    @GetMapping({"", "/"})
    public String getJoinedGroups(Model model) {
        Member currentMember = currentMemberService.getCurrentMember();
//        model.addAttribute("groups", groupService.findAllByLeaderId(currentMember.getId()));
        model.addAttribute("groups", groupService.findAll());
        return "joined-groups";
    }
}
