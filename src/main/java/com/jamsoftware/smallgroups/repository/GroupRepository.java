package com.jamsoftware.smallgroups.repository;

import com.jamsoftware.smallgroups.model.Category;
import com.jamsoftware.smallgroups.model.Group;
import com.jamsoftware.smallgroups.model.GroupForm;
import com.jamsoftware.smallgroups.model.Member;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupRepository {
    private final JdbcClient jdbcClient;

    public GroupRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Group> findAll() {
        String sql = """
                    SELECT gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender, gr.church_id
                    FROM groups gr
                    LEFT JOIN genders ge ON ge.id = gr.gender_id
                    ORDER BY gr.id
                """;

        List<Group> result = jdbcClient.sql(sql)
                .query((rs, rowNum) -> mapToGroupList(rs))
                .list();

        for (Group group : result) {
            group.setLeaders(findLeadersByGroupId(group.getId()));
            group.setCategories(findCategoriesByGroupId(group.getId()));
            group.setAges(findAgesByGroupId(group.getId()));
            group.setMembers(findMembersByGroupId(group.getId()));
        }

        return result;
    }

    public Optional<Group> findById(long id) {
        String sql = """
                    SELECT gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender, gr.church_id
                    FROM groups gr
                    LEFT JOIN genders ge ON ge.id = gr.gender_id
                    WHERE gr.id = :id
                """;

        return jdbcClient.sql(sql)
                .param("id", id)
                .query((rs, rowNum) -> mapToGroupList(rs))
                .optional()
                .map(group -> {
                    group.setLeaders(findLeadersByGroupId(group.getId()));
                    group.setCategories(findCategoriesByGroupId(group.getId()));
                    group.setAges(findAgesByGroupId(group.getId()));
                    group.setMembers(findMembersByGroupId(group.getId()));
                    return group;
                });
    }

    private List<String> findAgesByGroupId(long groupId) {
        String sql = """
                SELECT a.name
                FROM group_ages ga
                JOIN ages a ON a.id = ga.age_id
                WHERE ga.group_id = :groupId;
                """;
        List<String> ageNames = jdbcClient.sql(sql)
                .param("groupId", groupId)
                .query((rs, rowNum) -> rs.getString("name"))
                .list();
        return ageNames;
    }

    private List<Member> findLeadersByGroupId(long groupId) {
        String sql = """ 
                    SELECT m.app_user_id, m.first_name, m.last_name, m.email, m.home_phone, m.mobile_phone, m.church_id, m.id
                    FROM group_leaders gl
                    JOIN members m ON m.id = gl.member_id
                    WHERE gl.group_id = :groupId;
                """;
        List<Member> leaders = jdbcClient.sql(sql)
                .param("groupId", groupId)
                .query((rs, rowNum) -> Member.builder()
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .email(rs.getString("email"))
                        .homePhone(rs.getString("home_phone"))
                        .id(rs.getLong("id"))
                        .churchId(rs.getLong("church_id"))
                        .mobilePhone(rs.getString("mobile_phone"))
                        .build())
                .list();
        return leaders;
    }

    private List<Member> findMembersByGroupId(long groupId) {
        String sql = """ 
                    SELECT m.app_user_id, m.first_name, m.last_name, m.email, m.home_phone, m.mobile_phone, m.church_id, m.id
                    FROM group_members gm
                    JOIN members m ON m.id = gm.member_id
                    WHERE gm.group_id = :groupId;
                """;
        List<Member> members = jdbcClient.sql(sql)
                .param("groupId", groupId)
                .query((rs, rowNum) -> Member.builder()
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .email(rs.getString("email"))
                        .homePhone(rs.getString("home_phone"))
                        .id(rs.getLong("id"))
                        .churchId(rs.getLong("church_id"))
                        .mobilePhone(rs.getString("mobile_phone"))
                        .build())
                .list();
        return members;
    }

    public List<Category> findCategoriesByGroupId(long groupId) {
        String sql = """
                SELECT c.name, c.description, c.id
                FROM group_categories gc
                JOIN category c ON c.id = gc.category_id
                WHERE gc.group_id = :groupId;
                """;
        List<Category> categoryNames = jdbcClient.sql(sql)
                .param("groupId", groupId)
                .query((rs, rowNum) -> Category.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .build())
                .list();
        return categoryNames;
    }

    public List<Group> findJoinedGroupsByMemberId(long memberId) {
        String sql = """
                    SELECT  gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender, gr.church_id
                    FROM groups gr
                    LEFT JOIN genders ge ON ge.id = gr.gender_id
                    JOIN group_members gm ON gm.group_id = gr.id
                    where member_id = :memberId
                """;

        List<Group> result = jdbcClient.sql(sql)
                .param("memberId", memberId)
                .query((rs, rowNum) -> mapToGroupList(rs))
                .list();

        for (Group group : result) {
            group.setLeaders(findLeadersByGroupId(group.getId()));
            group.setCategories(findCategoriesByGroupId(group.getId()));
            group.setAges(findAgesByGroupId(group.getId()));
            group.setMembers(findMembersByGroupId(group.getId()));
        }

        return result;
    }

    public List<Group> findAllByChurchIdAndLeaderMemberIdNot(Long churchId, Long leaderId) {
        String sql = """
                SELECT DISTINCT  gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender, gr.church_id
                FROM group_leaders gl
                JOIN groups gr on gr.id = gl.group_id
                LEFT JOIN genders ge ON ge.id = gr.gender_id
                WHERE gr.church_id = :churchId
                AND NOT gl.member_id = :leaderId;
                """;

        List<Group> result = jdbcClient.sql(sql)
                .param("churchId", churchId)
                .param("leaderId", leaderId)
                .query((rs, rowNum) -> mapToGroupList(rs))
                .list();

        for (Group group : result) {
            group.setLeaders(findLeadersByGroupId(group.getId()));
            group.setCategories(findCategoriesByGroupId(group.getId()));
        }

        return result;
    }

    public List<Group> findAllByLeaderMemberId(Long memberLeaderId) {
        String sql = """
                SELECT gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender, gr.church_id
                FROM group_leaders gl
                JOIN groups gr on gr.id = gl.group_id
                LEFT JOIN genders ge ON ge.id = gr.gender_id
                WHERE gl.member_id = :leaderMemberId
                """;

        List<Group> result = jdbcClient.sql(sql)
                .param("leaderMemberId", memberLeaderId)
                .query((rs, rowNum) -> mapToGroupList(rs))
                .list();

        for (Group group : result) {
            group.setLeaders(findLeadersByGroupId(group.getId()));
            group.setCategories(findCategoriesByGroupId(group.getId()));
        }

        return result;
    }

    public long createGroupFromGroupForm(GroupForm groupForm) {
        String sql = """
                INSERT INTO groups (title, description, schedule, location, address, frequency, gender_id, church_id)
                VALUES (:title, :description, :schedule, :location, :address, :frequency,
                        (SELECT g.id FROM genders g WHERE g.name = :gender), :churchId)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .param("title", groupForm.getTitle())
                .param("description", groupForm.getDescription())
                .param("schedule", groupForm.getSchedule())
                .param("location", groupForm.getLocation())
                .param("address", groupForm.getAddress())
                .param("frequency", groupForm.getFrequency())
                .param("gender", groupForm.getGender())
                .param("churchId", groupForm.getChurchId())
                .update(keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId == null) {
            throw new IllegalStateException("Failed to create group: no generated id returned");
        }

        return generatedId.longValue();
    }

    public void assignLeaderToGroup(long groupId, Long leaderId) {
        String sql = """
                INSERT INTO group_leaders (group_id, member_id)
                VALUES (:groupId, :memberId)
                """;

        try {
            jdbcClient.sql(sql)
                    .param("groupId", groupId)
                    .param("memberId", leaderId)
                    .update();
        } catch (DuplicateKeyException ignored) {
            // Leader is already assigned to the group.
        }
    }

    public void assignCategoryToGroup(long groupId, Long categoryId) {
        String sql = """
                INSERT INTO group_categories (group_id, category_id)
                VALUES (:groupId, :categoryId)
                """;
        try {
            jdbcClient.sql(sql)
                    .param("groupId", groupId)
                    .param("categoryId", categoryId)
                    .update();
        } catch (DuplicateKeyException ignored) {
            // Leader is already assigned to the group.
        }
    }

    public void removeAllLeadersFromGroup(Long groupId) {
        String sql = """
                DELETE FROM group_leaders WHERE group_id = :groupId
                """;

        jdbcClient.sql(sql)
                .param("groupId", groupId)
                .update();
    }

    public void removeAllCategoriesFromGroup(Long groupId) {
        String sql = """
                DELETE FROM group_categories WHERE group_id = :groupId
                """;

        jdbcClient.sql(sql)
                .param("groupId", groupId)
                .update();
    }

    public void editGroupFromGroupForm(long id, GroupForm groupForm) {
        String sql = """
            UPDATE groups
            SET title = :title,
                description = :description,
                schedule = :schedule,
                location = :location,
                address = :address,
                frequency = :frequency,
                gender_id = (SELECT g.id FROM genders g WHERE g.name = :gender),
                church_id = :churchId
            WHERE id = :id
            """;

        int rowsAffected = jdbcClient.sql(sql)
                .param("id", id)
                .param("title", groupForm.getTitle())
                .param("description", groupForm.getDescription())
                .param("schedule", groupForm.getSchedule())
                .param("location", groupForm.getLocation())
                .param("address", groupForm.getAddress())
                .param("frequency", groupForm.getFrequency())
                .param("gender", groupForm.getGender())
                .param("churchId", groupForm.getChurchId())
                .update();

        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Edit failed: no group found with id " + id);
        } else if (rowsAffected < 0) {
            throw new IllegalStateException("Edit failed: unexpected row count (" + rowsAffected + ") for group id " + id);
        }
    }

    private Group mapToGroupList(ResultSet rs) throws SQLException {
        return Group.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .schedule(rs.getString("schedule"))
                .location(rs.getString("location"))
                .address(rs.getString("address"))
                .frequency(rs.getString("frequency"))
                .gender(rs.getString("gender"))
                .churchId(rs.getLong("church_id"))
                .build();
    }
}
