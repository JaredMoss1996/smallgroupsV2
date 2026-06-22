package com.jamsoftware.smallgroups.repository;

import com.jamsoftware.smallgroups.model.Category;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {

    private final JdbcClient jdbcClient;

    public CategoryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Category> findAllCategories() {
        String findAllCategories = """
                    SELECT id, name, description
                    FROM category
                    ORDER BY name
                """;
        return jdbcClient.sql(findAllCategories)
                .query((rs, rowNum) -> Category.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .build())
                .list();
    }
}
