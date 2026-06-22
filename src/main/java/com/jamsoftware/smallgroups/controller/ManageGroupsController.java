package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.model.Group;
import com.jamsoftware.smallgroups.model.GroupForm;
import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.repository.CategoryRepository;
import com.jamsoftware.smallgroups.service.CategoryService;
import com.jamsoftware.smallgroups.service.CurrentMemberService;
import com.jamsoftware.smallgroups.service.GroupService;
import com.jamsoftware.smallgroups.service.MemberService;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/groups")
public class ManageGroupsController {
    private final GroupService groupService;
    private final CurrentMemberService currentMemberService;
    private final MemberService memberService;
    private final CategoryService categoryService;

    public ManageGroupsController(GroupService groupService,
                                  CurrentMemberService currentMemberService,
                                  MemberService memberService,
                                  CategoryService categoryService) {
        this.groupService = groupService;
        this.currentMemberService = currentMemberService;
        this.memberService = memberService;
        this.categoryService = categoryService;
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
        Member currentMember = currentMemberService.getCurrentMember();
        GroupForm groupForm = new GroupForm();
        groupForm.setChurchId(currentMember.getChurchId());

        model.addAttribute("groupData", groupForm);
        model.addAttribute("isCreate", true);
        model.addAttribute("categories", categoryService.getAllCategories());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("MANAGE_CHURCHES"))) {
            model.addAttribute("leaders", memberService.getAllLeaders());
        } else {
            model.addAttribute("leaders", memberService.getLeadersByChurchId(currentMember.getChurchId()));
        }
        return "create-edit-group";
    }

    @PostMapping("/create")
    public String createGroupSubmit(@ModelAttribute GroupForm groupData, RedirectAttributes redirectAttributes) {
        String validationError = validateGroupData(groupData);
        if (validationError != null) {
            redirectAttributes.addFlashAttribute("error", validationError);
            return "redirect:/groups/create";
        }

        try {
            groupService.createGroup(groupData);
            redirectAttributes.addFlashAttribute("success", "Group created successfully.");
            return "redirect:/groups";
        } catch (IllegalArgumentException ex) {
            String errorMessage = ex.getMessage() == null ? "Invalid group data." : ex.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/groups/create";
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Some selected values are invalid (for example gender or leader). Please review and try again.");
            return "redirect:/groups/create";
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("error", "Database error while creating group. Please try again.");
            return "redirect:/groups/create";
        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("error", "Group could not be created due to an unexpected database state.");
            return "redirect:/groups/create";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Group creation failed due to an unexpected error. Please try again.");
            return "redirect:/groups/create";
        }
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("@authz.canEditGroup(#id)")
    public String editGroup(@PathVariable Long id, Model model) {
        Group group = groupService.findById(id).orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        GroupForm groupForm = groupService.groupToGroupForm(group);
        model.addAttribute("groupData", groupForm);
        model.addAttribute("isCreate", false);
        model.addAttribute("leaders", memberService.getLeadersByChurchId(group.getChurchId()));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "create-edit-group";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("@authz.canEditGroup(#id)")
    public String editGroupSubmit(@PathVariable Long id, @ModelAttribute GroupForm groupData, RedirectAttributes redirectAttributes) {
        if (id == null || id <= 0) {
            redirectAttributes.addFlashAttribute("error", "Invalid group id.");
            return "redirect:/groups";
        }

        String validationError = validateGroupData(groupData);
        if (validationError != null) {
            redirectAttributes.addFlashAttribute("error", validationError);
            return "redirect:/groups/edit/" + id;
        }

        try {
            groupService.editGroup(id, groupData);
            redirectAttributes.addFlashAttribute("success", "Group updated successfully.");
            return "redirect:/groups";
        } catch (IllegalArgumentException ex) {
            String errorMessage = ex.getMessage() == null ? "Invalid group data." : ex.getMessage();
            if (errorMessage.contains("no group found")) {
                errorMessage = "This group no longer exists. Please refresh and try again.";
            }
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/groups/edit/" + id;
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Some selected values are invalid (for example gender or leader). Please review and try again.");
            return "redirect:/groups/edit/" + id;
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("error", "Database error while updating group. Please try again.");
            return "redirect:/groups/edit/" + id;
        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("error", "Unexpected update result. Please try again or contact support.");
            return "redirect:/groups/edit/" + id;
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Group update failed due to an unexpected error. Please try again.");
            return "redirect:/groups/edit/" + id;
        }
    }

    private String validateGroupData(GroupForm groupData) {
        if (groupData == null) {
            return "Group data is missing.";
        }
        if (!StringUtils.hasText(groupData.getTitle())) {
            return "Group title is required.";
        }
        if (!StringUtils.hasText(groupData.getDescription())) {
            return "Group description is required.";
        }
        if (!StringUtils.hasText(groupData.getSchedule())) {
            return "Group schedule is required.";
        }
        if (!StringUtils.hasText(groupData.getLocation())) {
            return "Group location is required.";
        }
        if (!StringUtils.hasText(groupData.getAddress())) {
            return "Group address is required.";
        }
        if (!StringUtils.hasText(groupData.getGender())) {
            return "Group gender is required.";
        }
        if (groupData.getChurchId() == null || groupData.getChurchId() <= 0) {
            return "Church selection is required.";
        }
        if (groupData.getLeaderIds() == null || groupData.getLeaderIds().isEmpty()) {
            return "At least one leader must be selected.";
        }
        if (groupData.getLeaderIds().stream().anyMatch(leaderId -> leaderId == null || leaderId <= 0)) {
            return "One or more selected leaders are invalid.";
        }

        return null;
    }
}
