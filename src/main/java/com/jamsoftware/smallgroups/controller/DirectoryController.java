package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.service.CategoryService;
import com.jamsoftware.smallgroups.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DirectoryController {
    GroupService groupService;
    CategoryService categoryService;

    public DirectoryController(GroupService groupService, CategoryService categoryService) {
        this.groupService = groupService;
        this.categoryService = categoryService;
    }

    @GetMapping({"/directory", "/"})
    public String getDirectory(Model model) {
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "directory";
    }
}
