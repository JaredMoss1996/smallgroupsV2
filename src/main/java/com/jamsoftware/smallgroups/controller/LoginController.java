package com.jamsoftware.smallgroups.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final InMemoryUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public LoginController(InMemoryUserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
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

        UserDetails user = User.withUsername(username)
            .password(passwordEncoder.encode(password))
            .roles("USER")
            .build();

        userDetailsManager.createUser(user);

        redirectAttributes.addFlashAttribute("success", "Account created. Please sign in.");
        return "redirect:/login";
    }
}
