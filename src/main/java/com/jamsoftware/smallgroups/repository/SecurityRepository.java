package com.jamsoftware.smallgroups.repository;

import com.jamsoftware.smallgroups.model.Group;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityRepository {
    private final JdbcClient jdbcClient;

    public SecurityRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public boolean isGroupLeader(long group_id, Long member_id) {
        String sql = """
                    SELECT 1
                    FROM group_leaders
                    WHERE group_id = :group_id
                    AND member_id = :member_id
                """;

        boolean isGroupLeader = jdbcClient.sql(sql)
                .param("group_id", group_id)
                .param("member_id", member_id)
                .query(Boolean.class)
                .optional().orElse(false);

        return isGroupLeader;
    }


}
