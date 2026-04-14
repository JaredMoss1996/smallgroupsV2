package com.jamsoftware.smallgroups.model;

import lombok.Data;

@Data
public class AppUser {
    private final Long id;
    private final String username;
    private final String password;
    private final boolean enabled;

    public AppUser(Long id, String username, String password, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
}
