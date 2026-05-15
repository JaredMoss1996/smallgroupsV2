package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.service.CurrentMemberService;
import com.jamsoftware.smallgroups.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/leaders")
public class ManageLeaderController {

    private final MemberService memberService;
    private final CurrentMemberService currentMemberService;

    public ManageLeaderController(MemberService memberService, CurrentMemberService currentMemberService) {
        this.memberService = memberService;
        this.currentMemberService = currentMemberService;
    }

    @GetMapping({"", "/"})
    public String getManageLeaders(Model model) {
        model.addAttribute("leaders", memberService.getLeadersByChurchId(currentMemberService.getCurrentMember().getChurchId()));
        return "manage-leaders";
    }

    @GetMapping("/assign")
    public String createGroup(Model model) {
        return "assign-leader";
    }

    @GetMapping("/{id}")
    @PreAuthorize("@authz.canEditLeader(#id)")
    public String getLeaderDetails(@PathVariable long id, Model model) {
        memberService.getMemberById(id).ifPresent(member -> model.addAttribute("leader", member));
        return "leader-details";
    }

}
