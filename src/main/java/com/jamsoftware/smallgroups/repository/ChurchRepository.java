package com.jamsoftware.smallgroups.repository;

import com.jamsoftware.smallgroups.model.Church;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChurchRepository {

    private final JdbcClient jdbcClient;

    public ChurchRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Church> findAllChurches() {
        String sql = """
                    SELECT id, name, address, contact_info
                    FROM churches
                    ORDER BY name
                """;
        return jdbcClient.sql(sql)
                .query((rs, rowNum) -> Church.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .address(rs.getString("address"))
                        .contactInfo(rs.getString("contact_info"))
                        .build())
                .list();
    }
}

