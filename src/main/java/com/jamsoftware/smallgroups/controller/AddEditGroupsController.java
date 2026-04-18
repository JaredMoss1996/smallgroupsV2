package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.service.DirectoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/groups/manage")
public class AddEditGroupsController {
    DirectoryService directoryService;

    public AddEditGroupsController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @GetMapping({"", "/"})
    public String getDirectory(Model model) {
        model.addAttribute("groups", directoryService.findAll());
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
