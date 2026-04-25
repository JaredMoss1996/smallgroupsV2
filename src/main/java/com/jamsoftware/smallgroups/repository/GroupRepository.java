package com.jamsoftware.smallgroups.repository;

import com.jamsoftware.smallgroups.model.Group;
import com.jamsoftware.smallgroups.model.Member;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GroupRepository {
    private final JdbcClient jdbcClient;

    public GroupRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // ===================== SELECT ALL =====================
    public List<Group> findAll() {
        String sql = """
                    SELECT gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender
                    FROM groups gr
                    LEFT JOIN genders ge ON ge.id = gr.id
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

    public Group findById(long id) {
        String sql = """
                    SELECT gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender
                    FROM groups gr
                    LEFT JOIN genders ge ON ge.id = gr.id
                    WHERE gr.id = :id
                """;

        Group group = jdbcClient.sql(sql)
                .param("id", id)
                .query((rs, rowNum) -> mapToGroupList(rs))
                .single();

        group.setLeaders(findLeadersByGroupId(group.getId()));
        group.setCategories(findCategoriesByGroupId(group.getId()));
        group.setAges(findAgesByGroupId(group.getId()));
        group.setMembers(findMembersByGroupId(group.getId()));

        return group;
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

    public List<String> findCategoriesByGroupId(long groupId) {
        String sql = """
                SELECT c.name
                FROM group_categories gc
                JOIN category c ON c.id = gc.category_id
                WHERE gc.group_id = :groupId;
                """;
        List<String> categoryNames = jdbcClient.sql(sql)
                .param("groupId", groupId)
                .query((rs, rowNum) -> rs.getString("name"))
                .list();
        return categoryNames;
    }

    public List<Group> findJoinedGroupsByMemberId(long memberId) {
        String sql = """
                    SELECT  gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender
                    FROM groups gr
                    LEFT JOIN genders ge ON ge.id = gr.id
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
                SELECT DISTINCT  gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender
                FROM group_leaders gl
                JOIN groups gr on gr.id = gl.group_id
                LEFT JOIN genders ge ON ge.id = gr.id
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
                SELECT gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender
                FROM group_leaders gl
                JOIN groups gr on gr.id = gl.group_id
                LEFT JOIN genders ge ON ge.id = gr.id
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
                .build();
    }
}
