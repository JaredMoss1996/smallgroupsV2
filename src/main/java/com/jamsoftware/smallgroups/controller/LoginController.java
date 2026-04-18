package com.jamsoftware.smallgroups.controller;

import com.jamsoftware.smallgroups.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LoginController {

    private final UserService userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserService userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // resolves to src/main/resources/templates/login.html
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register"; // resolves to src/main/resources/templates/register.html
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String username,
                                 @RequestParam String password,
                                 RedirectAttributes redirectAttributes) {
        // Basic validation
        if (username == null || username.trim().isEmpty() || password == null || password.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "Username is required and password must be at least 6 characters.");
            return "redirect:/register";
        }

        if (userDetailsManager.userExists(username)) {
            redirectAttributes.addFlashAttribute("error", "User already exists.");
            return "redirect:/register";
        }

        userDetailsManager.createUser(username, password, List.of("USER"));

        redirectAttributes.addFlashAttribute("success", "Account created. Please sign in.");
        return "redirect:/login";
    }
}
