package com.jamsoftware.smallgroups.repository;

import com.jamsoftware.smallgroups.model.Member;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final JdbcClient jdbcClient;

    public MemberRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<Member> getMemberByUserId(Long userId) {
        String sql = """
                SELECT id, first_name, last_name, email, home_phone, cell_phone, church_id
                FROM members
                WHERE app_user_id = :userId
                """;
        return jdbcClient.sql(sql)
                .param("userId", userId)
                .query((rs, rowNum) -> Member.builder()
                        .id(rs.getLong("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .email(rs.getString("email"))
                        .homePhone(rs.getString("home_phone"))
                        .mobilePhone(rs.getString("mobile_phone"))
                        .church_id(rs.getLong("church_id"))
                        .build())
                .optional();
    }
}
