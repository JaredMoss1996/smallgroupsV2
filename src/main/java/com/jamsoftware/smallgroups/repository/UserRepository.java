package com.jamsoftware.smallgroups.repository;

import com.jamsoftware.smallgroups.model.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public boolean userExists(String username) {
        Integer count = jdbcClient.sql("""
                SELECT COUNT(*)
                FROM app_user
                WHERE username = :username
            """)
                .param("username", username)
                .query(Integer.class)
                .single();

        return count > 0;
    }

    public long createUser(String username, String encodedPassword) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                INSERT INTO app_user (username, password, enabled)
                VALUES (:username, :password, true)
            """)
                .param("username", username)
                .param("password", encodedPassword)
                .update(keyHolder);
        Number userId = (Number) Objects.requireNonNull(keyHolder.getKeys()).get("id");
        return userId.longValue();
    }

    public void assignUserRoles(List<String> roles, long userId) {
        for (String roleName : roles) {
            Long roleId = jdbcClient.sql("""
                    SELECT id
                    FROM role
                    WHERE name = :name
                """)
                    .param("name", roleName)
                    .query(Long.class)
                    .optional()
                    .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));

            jdbcClient.sql("""
                    INSERT INTO user_role (user_id, role_id)
                    VALUES (:userId, :roleId)
                """)
                    .param("userId", userId)
                    .param("roleId", roleId)
                    .update();
        }
    }

    // find user by username
    public Optional<User> findByUsername(String username) {
        return jdbcClient.sql("""
                SELECT id, username, password, enabled
                FROM app_user
                WHERE username = :username
            """)
                .param("username", username)
                .query((rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("enabled")
                ))
                .optional();
    }

    // find role names for a user id
    public List<String> findRoleNamesByUserId(Long userId) {
        return jdbcClient.sql("""
                SELECT r.name
                FROM role r
                JOIN user_role ur ON r.id = ur.role_id
                WHERE ur.user_id = :userId
            """)
                .param("userId", userId)
                .query((rs, rowNum) -> rs.getString("name"))
                .list();
    }
}
