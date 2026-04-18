//package com.jamsoftware.smallgroups.repository;
//
//import com.jamsoftware.smallgroups.model.*;
//import org.springframework.jdbc.core.simple.JdbcClient;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
//@Repository
//public class DirectoryRepository {
//    private final JdbcClient jdbcClient;
//
//    public DirectoryRepository(JdbcClient jdbcClient) {
//        this.jdbcClient = jdbcClient;
//    }
//
//    private List<Member> findMembersByGroupId(long groupId) {
//        String sql = """
//                    SELECT m.app_user_id, m.first_name, m.last_name
//                    FROM group_members gm
//                    JOIN members m ON m.id = gm.member_id
//                    WHERE gm.group_id = :groupId;
//                """;
//        List<Member> members = jdbcClient.sql(sql)
//                .param("groupId", groupId)
//                .query((rs, rowNum) -> Member.builder()
//                        .appUserId(rs.getLong("app_user_id"))
//                        .firstName(rs.getString("first_name"))
//                        .lastName(rs.getString("last_name"))
//                        .build())
//                .list();
//        return members;
//    }
//
//    // ===================== INSERT =====================
//    @Transactional
//    public long insert(GroupCard group) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        String insertGroup = """
//                    INSERT INTO groups (title, description, schedule, location, address, contact_info, gender, age)
//                    VALUES (:title, :description, :schedule, :location, :address, :contactInfo, :gender, :age)
//                """;
//
//        jdbcClient.sql(insertGroup)
//                .param("title", group.getTitle())
//                .param("description", group.getDescription())
//                .param("schedule", group.getSchedule())
//                .param("location", group.getLocation())
//                .param("address", group.getAddress())
//                .param("contactInfo", group.getContactInfo())
//                .param("gender", group.getGender())
////                .param("age", group.getAges())
//                .update(keyHolder);
//
//        Number groupId = (Number) Objects.requireNonNull(keyHolder.getKeys()).get("id");
//
//        if (group.getLeaders() != null) {
//            for (Member leader : group.getLeaders()) {
//                jdbcClient.sql("""
//                                    INSERT INTO group_leaders (first_name, last_name, group_id)
//                                    VALUES (:firstName, :lastName, :groupId)
//                                """)
//                        .param("firstName", leader.getFirstName())
//                        .param("lastName", leader.getLastName())
//                        .param("groupId", groupId)
//                        .update();
//            }
//        }
//
//        if (group.getCategories() != null) {
//            List<Long> categoryIds = findAllCategoryIds(group.getCategories());
//            for (Long categoryId : categoryIds) {
//                jdbcClient.sql("""
//                                    INSERT INTO group_categories (group_id, category_id)
//                                    VALUES (:groupId, :categoryId)
//                                """)
//                        .param("groupId", groupId)
//                        .param("categoryId", categoryId)
//                        .update();
//            }
//        }
//
//        return groupId.longValue();
//    }
//
//    // ===================== UPDATE =====================
//    @Transactional
//    public void update(GroupCard group) {
//        jdbcClient.sql("""
//                            UPDATE groups
//                            SET title = :title,
//                                description = :description,
//                                schedule = :schedule,
//                                location = :location,
//                                address = :address,
//                                contact_info = :contactInfo,
//                                gender = :gender,
//                                age = :age
//                            WHERE id = :id
//                        """)
//                .param("id", group.getId())
//                .param("title", group.getTitle())
//                .param("description", group.getDescription())
//                .param("schedule", group.getSchedule())
//                .param("location", group.getLocation())
//                .param("address", group.getAddress())
//                .param("contactInfo", group.getContactInfo())
//                .param("gender", group.getGender())
////                .param("age", group.getAge()) TODO insert all ages
//                .update();
//
//        jdbcClient.sql("DELETE FROM group_leaders WHERE group_id = :id")
//                .param("id", group.getId())
//                .update();
//
//        if (group.getLeaders() != null) {
//            for (Member leader : group.getLeaders()) {
//                jdbcClient.sql("""
//                                    INSERT INTO group_leaders (first_name, last_name, group_id)
//                                    VALUES (:firstName, :lastName, :groupId)
//                                """)
//                        .param("firstName", leader.getFirstName())
//                        .param("lastName", leader.getLastName())
//                        .param("groupId", group.getId())
//                        .update();
//            }
//        }
//
//        jdbcClient.sql("DELETE FROM group_categories WHERE group_id = :id")
//                .param("id", group.getId())
//                .update();
//
//        if (group.getCategories() != null) {
//            List<Long> categoryIds = findAllCategoryIds(group.getCategories());
//            for (Long categoryId : categoryIds) {
//                jdbcClient.sql("""
//                                    INSERT INTO group_categories (group_id, group_categories)
//                                    VALUES (:groupId, :category)
//                                """)
//                        .param("groupId", group.getId())
//                        .param("category", categoryIds)
//                        .update();
//            }
//        }
//    }
//
//    public List<Long> findAllCategoryIds(List<String> categoryNames) {
//        List<Long> categoryIds = jdbcClient.sql("SELECT id FROM category WHERE name in (:categories)")
//                .param("categories", categoryNames)
//                .query((rs, rowNum) -> rs.getLong("id"))
//                .list();
//        return categoryIds;
//    }
//
//    // ===================== DELETE =====================
//    public void deleteGroupById(long id) {
//        jdbcClient.sql("DELETE FROM groups WHERE id = :id")
//                .param("id", id)
//                .update();
//    }
//
//}
