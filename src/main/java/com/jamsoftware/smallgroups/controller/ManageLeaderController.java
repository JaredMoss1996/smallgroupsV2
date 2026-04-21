package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.model.Group;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/leaders")
public class ManageLeaderController {
    @GetMapping({"", "/"})
    public String getDirectory(Model model) {
//        model.addAttribute("leaders", memberServive.getLeaderByChurchId());
        return "manage-leaders";
    }

    @GetMapping("/assign")
    public String createGroup(Model model) {
        return "assign-leader";
    }

    @PostMapping("/create")
    public String createGroupSubmit(@ModelAttribute Group groupData, Model model) {
        return "manage-leaders";
    }
}
