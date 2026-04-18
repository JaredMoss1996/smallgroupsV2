package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.repository.UserRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public boolean userExists(String username) {
        return userRepository.userExists(username);
    }

    public void createUser(String username, String rawPassword, List<String> roles) {

        if (userExists(username)) {
            throw new IllegalStateException("User already exists");
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        long userId = userRepository.createUser(username, encodedPassword);
        userRepository.assignUserRoles(roles, userId);
    }

    public void getCurrentUser() {

    }
}