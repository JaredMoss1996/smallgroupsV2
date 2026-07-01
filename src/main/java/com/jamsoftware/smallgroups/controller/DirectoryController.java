package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.service.CategoryService;
import com.jamsoftware.smallgroups.service.ChurchService;
import com.jamsoftware.smallgroups.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DirectoryController {
    GroupService groupService;
    CategoryService categoryService;
    ChurchService churchService;

    public DirectoryController(GroupService groupService,
                               CategoryService categoryService,
                               ChurchService churchService) {
        this.groupService = groupService;
        this.categoryService = categoryService;
        this.churchService = churchService;
    }

    @GetMapping({"/directory", "/"})
    public String getDirectory(Model model) {
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("churchesMap", churchService.findAllChurchesMapById());
        return "directory";
    }
}
