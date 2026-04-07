package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.service.DirectoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DirectoryController {
    DirectoryService directoryService;

     public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @GetMapping({"/directory", "/"})
     public String getDirectory(Model model) {
         model.addAttribute("groups", directoryService.findAll());
        return "directory";
    }
}
