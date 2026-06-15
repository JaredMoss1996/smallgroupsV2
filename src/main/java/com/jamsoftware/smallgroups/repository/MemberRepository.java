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

    public List<Member> getLeadersByChurchId(long churchId) {
        String sql = """
                SELECT m.id, m.first_name, m.last_name, m.email, m.home_phone, m.mobile_phone, m.church_id, app_user_id
                FROM members m
                JOIN app_user au ON au.id = m.app_user_id
                JOIN roles r on r.id = au.role_id
                WHERE church_id = :churchId AND r.name = 'LEADER'
                """;
        return jdbcClient.sql(sql)
                .param("churchId", churchId)
                .query((rs, rowNum) -> Member.builder()
                        .id(rs.getLong("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .email(rs.getString("email"))
                        .homePhone(rs.getString("home_phone"))
                        .mobilePhone(rs.getString("mobile_phone"))
                        .churchId(rs.getLong("church_id"))
                        .appUserId(rs.getLong("app_user_id"))
                        .build())
                .list();
    }

    public List<Member> getAllLeaders() {
        String sql = """
                SELECT m.id, m.first_name, m.last_name, m.email, m.home_phone, m.mobile_phone, m.church_id, app_user_id
                FROM members m
                JOIN app_user au ON au.id = m.app_user_id
                JOIN roles r on r.id = au.role_id
                WHERE r.name = 'LEADER'
                """;
        return jdbcClient.sql(sql)
                .query((rs, rowNum) -> Member.builder()
                        .id(rs.getLong("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .email(rs.getString("email"))
                        .homePhone(rs.getString("home_phone"))
                        .mobilePhone(rs.getString("mobile_phone"))
                        .churchId(rs.getLong("church_id"))
                        .appUserId(rs.getLong("app_user_id"))
                        .build())
                .list();
    }

    public Optional<Member> getMemberById(long memberId) {
        String sql = """
                SELECT id, first_name, last_name, email, home_phone, mobile_phone, church_id, app_user_id
                FROM members
                WHERE id = :memberId
                """;
        return jdbcClient.sql(sql)
                .param("memberId", memberId)
                .query((rs, rowNum) -> Member.builder()
                        .id(rs.getLong("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .email(rs.getString("email"))
                        .homePhone(rs.getString("home_phone"))
                        .mobilePhone(rs.getString("mobile_phone"))
                        .churchId(rs.getLong("church_id"))
                        .appUserId(rs.getLong("app_user_id"))
                        .build())
                .optional();
    }

    public Optional<Member> getMemberByUserId(Long userId) {
        String sql = """
                SELECT id, first_name, last_name, email, home_phone, mobile_phone, church_id, app_user_id
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
                        .churchId(rs.getLong("church_id"))
                        .appUserId(rs.getLong("app_user_id"))
                        .build())
                .optional();
    }


}
