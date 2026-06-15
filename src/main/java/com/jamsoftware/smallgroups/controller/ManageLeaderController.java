package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.repository.UserRepository;
import com.jamsoftware.smallgroups.service.CurrentMemberService;
import com.jamsoftware.smallgroups.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;;

    public ManageLeaderController(MemberService memberService, CurrentMemberService currentMemberService, UserRepository userRepository) {
        this.memberService = memberService;
        this.currentMemberService = currentMemberService;
        this.userRepository = userRepository;
    }

    @GetMapping({"", "/"})
    public String getManageLeaders(Model model) {
        Member currentMember = currentMemberService.getCurrentMember();
        model.addAttribute("leaders", memberService.getLeadersByChurchId(currentMember.getChurchId()));
        String currentUserRole = userRepository.getUserRole(currentMember.getAppUserId())
                .orElseThrow(() -> new RuntimeException("Role not found for user id: " + currentMember.getAppUserId()));
        if (currentUserRole.equals("SUPER_ADMIN")) {
            model.addAttribute("leaders", memberService.getAllLeaders());
        }
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
