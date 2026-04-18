package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DirectoryController {
    GroupService groupService;

     public DirectoryController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping({"/directory", "/"})
     public String getDirectory(Model model) {
         model.addAttribute("groups", groupService.findAll());
        return "directory";
    }
}
