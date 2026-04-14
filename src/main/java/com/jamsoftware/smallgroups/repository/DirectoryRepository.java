package com.jamsoftware.smallgroups.repository;

import com.jamsoftware.smallgroups.model.*;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class DirectoryRepository {
    private final JdbcClient jdbcClient;

    public DirectoryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // ===================== SELECT ALL =====================
    public List<GroupCard> findAll() {
        String sql = """
                    SELECT gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.contact_info, gr.frequency, ge.name as gender
                    FROM groups gr
                    JOIN genders ge ON ge.id = gr.id
                    ORDER BY id
                """;

        List<GroupCard> result = jdbcClient.sql(sql)
                .query((rs, rowNum) -> GroupCard.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .schedule(rs.getString("schedule"))
                        .location(rs.getString("location"))
                        .address(rs.getString("address"))
                        .contactInfo(rs.getString("contact_info"))
                        .frequency(rs.getString("frequency"))
                        .gender(rs.getString("gender"))
                        .build())
                .list();

        for (GroupCard group : result) {
            group.setLeaders(findLeadersByGroupId(group.getId()));
            group.setCategories(findCategoriesByGroupId(group.getId()));
        }

        return result;
    }

    private List<Member> findLeadersByGroupId(long groupId) {
        String sql = """
                    SELECT m.app_user_id, m.first_name, m.last_name
                    FROM group_leaders gl
                    JOIN members m ON m.id = gl.member_id
                    WHERE gl.group_id = :groupId;
                """;
        List<Member> leaders = jdbcClient.sql(sql)
                .param("groupId", groupId)
                .query((rs, rowNum) -> Member.builder()
                        .appUserId(rs.getLong("app_user_id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .build())
                .list();
        return leaders;
    }

    private List<Member> findMembersByGroupId(long groupId) {
        String sql = """
                    SELECT m.app_user_id, m.first_name, m.last_name
                    FROM group_members gm
                    JOIN members m ON m.id = gm.member_id
                    WHERE gm.group_id = :groupId;
                """;
        List<Member> members = jdbcClient.sql(sql)
                .param("groupId", groupId)
                .query((rs, rowNum) -> Member.builder()
                        .appUserId(rs.getLong("app_user_id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .build())
                .list();
        return members;
    }


    public List<String> findCategoriesByGroupId(long groupId) {
        String sql = """
                SELECT c.name
                FROM group_categories gc
                JOIN category c ON c.id = gc.category_id
                WHERE gc.group_id = :groupId;
                """;
        List<String> categoryNames =  jdbcClient.sql(sql)
                .param("groupId", groupId)
                .query((rs, rowNum) -> rs.getString("name"))
                .list();
        return categoryNames;
    }

//    public List<Leader> findAllLeadersByGroupId(long groupId) {
//        String sql = "SELECT first_name, last_name FROM group_leaders WHERE group_id = :groupId";
//        return jdbcClient.sql(sql)
//                .param("groupId", groupId)
//                .query((rs, rowNum) -> {
//                    Leader leader = new Leader();
//                    leader.setFirstName(rs.getString("first_name"));
//                    leader.setLastName(rs.getString("last_name"));
//                    return leader;
//                })
//                .list();
//    }

//    // ===================== SELECT BY ID =====================
//    public Optional<GroupCard> findById(long id) {
//        String sql = """
//                    SELECT g.*,
//                           gl.id as leader_id, gl.first_name, gl.last_name,
//                           gc.id as category_id, gc.group_categories
//                    FROM groups g
//                    LEFT JOIN group_leaders gl ON g.id = gl.group_id
//                    LEFT JOIN group_categories gc ON g.id = gc.group_id
//                    WHERE g.id = :id
//                """;
//
//        List<GroupCard> results = jdbcClient.sql(sql)
//                .param("id", id)
//                .query((rs, rowNum) -> groupCardExtractor(rs))
//                .single();
//
//        return results.stream().findFirst();
//    }

    // ===================== INSERT =====================
    @Transactional
    public long insert(GroupCard group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertGroup = """
                    INSERT INTO groups (title, description, schedule, location, address, contact_info, gender, age)
                    VALUES (:title, :description, :schedule, :location, :address, :contactInfo, :gender, :age)
                """;

        jdbcClient.sql(insertGroup)
                .param("title", group.getTitle())
                .param("description", group.getDescription())
                .param("schedule", group.getSchedule())
                .param("location", group.getLocation())
                .param("address", group.getAddress())
                .param("contactInfo", group.getContactInfo())
                .param("gender", group.getGender())
                .param("age", group.getAge())
                .update(keyHolder);

        Number groupId = (Number) Objects.requireNonNull(keyHolder.getKeys()).get("id");

        if (group.getLeaders() != null) {
            for (Member leader : group.getLeaders()) {
                jdbcClient.sql("""
                                    INSERT INTO group_leaders (first_name, last_name, group_id)
                                    VALUES (:firstName, :lastName, :groupId)
                                """)
                        .param("firstName", leader.getFirstName())
                        .param("lastName", leader.getLastName())
                        .param("groupId", groupId)
                        .update();
            }
        }

        if (group.getCategories() != null) {
            List<Long> categoryIds = findAllCategoryIds(group.getCategories());
            for (Long categoryId : categoryIds) {
                jdbcClient.sql("""
                                    INSERT INTO group_categories (group_id, category_id)
                                    VALUES (:groupId, :categoryId)
                                """)
                        .param("groupId", groupId)
                        .param("categoryId", categoryId)
                        .update();
            }
        }

        return groupId.longValue();
    }

    // ===================== UPDATE =====================
    @Transactional
    public void update(GroupCard group) {
        jdbcClient.sql("""
                            UPDATE groups
                            SET title = :title,
                                description = :description,
                                schedule = :schedule,
                                location = :location,
                                address = :address,
                                contact_info = :contactInfo,
                                gender = :gender,
                                age = :age
                            WHERE id = :id
                        """)
                .param("id", group.getId())
                .param("title", group.getTitle())
                .param("description", group.getDescription())
                .param("schedule", group.getSchedule())
                .param("location", group.getLocation())
                .param("address", group.getAddress())
                .param("contactInfo", group.getContactInfo())
                .param("gender", group.getGender())
                .param("age", group.getAge())
                .update();

        jdbcClient.sql("DELETE FROM group_leaders WHERE group_id = :id")
                .param("id", group.getId())
                .update();

        if (group.getLeaders() != null) {
            for (Member leader : group.getLeaders()) {
                jdbcClient.sql("""
                                    INSERT INTO group_leaders (first_name, last_name, group_id)
                                    VALUES (:firstName, :lastName, :groupId)
                                """)
                        .param("firstName", leader.getFirstName())
                        .param("lastName", leader.getLastName())
                        .param("groupId", group.getId())
                        .update();
            }
        }

        jdbcClient.sql("DELETE FROM group_categories WHERE group_id = :id")
                .param("id", group.getId())
                .update();

        if (group.getCategories() != null) {
            List<Long> categoryIds = findAllCategoryIds(group.getCategories());
            for (Long categoryId : categoryIds) {
                jdbcClient.sql("""
                                    INSERT INTO group_categories (group_id, group_categories)
                                    VALUES (:groupId, :category)
                                """)
                        .param("groupId", group.getId())
                        .param("category", categoryIds)
                        .update();
            }
        }
    }

    public List<Long> findAllCategoryIds(List<String> categoryNames) {
        List<Long> categoryIds = jdbcClient.sql("SELECT id FROM category WHERE name in (:categories)")
                .param("categories", categoryNames)
                .query((rs, rowNum) -> rs.getLong("id"))
                .list();
        return categoryIds;
    }

    // ===================== DELETE =====================
    public void deleteGroupById(long id) {
        jdbcClient.sql("DELETE FROM groups WHERE id = :id")
                .param("id", id)
                .update();
    }

    // ===================== RESULT SET EXTRACTOR =====================
    private List<GroupCard> groupCardExtractor(ResultSet rs) throws SQLException {
        ArrayList<GroupCard> result = new ArrayList<>();
//
//        do {
//            long groupId = rs.getLong("id");
//
//            GroupCard group = new GroupCard();
//            group.setId(groupId);
//            group.setTitle(rs.getString("title"));
//            group.setDescription(rs.getString("description"));
//            group.setSchedule(rs.getString("schedule"));
//            group.setLocation(rs.getString("location"));
//            group.setAddress(rs.getString("address"));
//            group.setContactInfo(rs.getString("contact_info"));
//            group.setGender(rs.getString("gender"));
//            group.setAge(rs.getString("age"));
//            group.setFrequency(rs.getString("frequency"));
//            group.setLeaders(new ArrayList<>());
//            group.setCategories(new ArrayList<>());
//
//            result.add(group);
//        } while (rs.next());
        return result;
    }

}
