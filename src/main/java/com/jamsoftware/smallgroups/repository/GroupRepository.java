package com.jamsoftware.smallgroups.repository;

import com.jamsoftware.smallgroups.model.GroupCard;
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
    public List<GroupCard> findAll() {
        String sql = """
                    SELECT gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender
                    FROM groups gr
                    LEFT JOIN genders ge ON ge.id = gr.id
                    ORDER BY id
                """;

        List<GroupCard> result = jdbcClient.sql(sql)
                .query((rs, rowNum) -> mapToGroupCardList(rs))
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
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .build())
                .list();
        return leaders;
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


    public List<GroupCard> findAllByLeaderMemberId(Long memberLeaderId) {
        String sql = """
                SELECT gr.id, gr.title, gr.description, gr.schedule, gr.location, gr.address, gr.frequency, ge.name as gender
                FROM group_leaders gl
                JOIN groups gr on gr.id = gl.group_id
                JOIN genders ge ON ge.id = gr.id
                WHERE gl.member_id = :leaderMemberId
                """;

        List<GroupCard> result = jdbcClient.sql(sql)
                .param("leaderMemberId", memberLeaderId)
                .query((rs, rowNum) -> mapToGroupCardList(rs))
                .list();

        for (GroupCard group : result) {
            group.setLeaders(findLeadersByGroupId(group.getId()));
            group.setCategories(findCategoriesByGroupId(group.getId()));
        }

        return result;
    }

    private GroupCard mapToGroupCardList(ResultSet rs) throws SQLException {
        return GroupCard.builder()
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
