package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.AppUser;
import com.jamsoftware.smallgroups.model.CustomUserDetails;
import com.jamsoftware.smallgroups.repository.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {

         AppUser user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long userId = user.getId();

        List<String> permissions = userRepository.findPermissionNamesByUserId(userId);

        List<GrantedAuthority> authorities = permissions.stream()
                .map(permission -> (GrantedAuthority) new SimpleGrantedAuthority(permission))
                .toList();

        return CustomUserDetails.builder()
                .userId(userId)
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .authorities(authorities)
                .build();
    }
}
