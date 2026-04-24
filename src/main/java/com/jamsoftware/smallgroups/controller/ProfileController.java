package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.service.CurrentMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final CurrentMemberService currentMemberService;

    public ProfileController(CurrentMemberService currentMemberService) {
        this.currentMemberService = currentMemberService;
    }

    @GetMapping({"", "/"})
    public String getProfile(Model model) {
        Member currentMember = currentMemberService.getCurrentMember();
        model.addAttribute("member", currentMember);
        return "profile";
    }
}

