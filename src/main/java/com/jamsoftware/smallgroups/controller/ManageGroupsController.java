package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.model.Group;
import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.service.CurrentMemberService;
import com.jamsoftware.smallgroups.service.GroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/groups")
public class ManageGroupsController {
    private final GroupService groupService;
    private final CurrentMemberService currentMemberService;

    public ManageGroupsController(GroupService groupService, CurrentMemberService currentMemberService) {
        this.groupService = groupService;
        this.currentMemberService = currentMemberService;
    }

    @GetMapping({"", "/"})
    public String getDirectory(Model model) {
        Member currentMember = currentMemberService.getCurrentMember();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("groups", groupService.findAllByLeaderId(currentMember.getId()));
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("MANAGE_LEADERS"))) {
            model.addAttribute("leaderGroups", groupService.findAllByChurchIdAndLeaderIdNot(currentMember.getChurchId(), currentMember.getId()));
        }
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("MANAGE_CHURCHES"))) {
            model.addAttribute("superAdminGroups", groupService.findAll());
        }
        return "manage-groups";
    }

    @GetMapping("/create")
    public String createGroup(Model model) {
        model.addAttribute("groupData", new Group());
        model.addAttribute("isCreate", true);
        return "create-edit-group";
    }

    @PostMapping("/create")
    public String createGroupSubmit(@ModelAttribute Group groupData, Model model) {
        return "redirect:/groups";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("@authz.canEditGroup(#id)")
    public String editGroup(@PathVariable Long id, Model model) {
        model.addAttribute("groupData", groupService.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id)));
        model.addAttribute("isCreate", false);
        return "create-edit-group";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("@authz.canEditGroup(#id)")
    public String editGroupSubmit(@PathVariable Long id, @ModelAttribute Group groupData, RedirectAttributes redirectAttributes) {
        int rowsUpdated = groupService.editGroup(id, groupData);

        if (rowsUpdated <= 0) {
            redirectAttributes.addFlashAttribute("error", "Group update failed. Please try again.");
            return "redirect:/groups/edit/" + id;
        }

        redirectAttributes.addFlashAttribute("success", "Group updated successfully.");
        return "redirect:/groups";
    }
}
