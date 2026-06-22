package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.Category;
import com.jamsoftware.smallgroups.model.Group;
import com.jamsoftware.smallgroups.model.GroupForm;
import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

     public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public void createGroup(GroupForm groupForm) {
        if (groupForm == null) {
            throw new IllegalArgumentException("Group form data is required.");
        }
        if (groupForm.getChurchId() == null || groupForm.getChurchId() <= 0) {
            throw new IllegalArgumentException("Church selection is required.");
        }

        long groupId = groupRepository.createGroupFromGroupForm(groupForm);
        assignLeadersToGroup(groupForm.getLeaderIds(), groupId);
    }

    public List<Group> findAllByLeaderId(Long memberLeaderId) {
         return groupRepository.findAllByLeaderMemberId(memberLeaderId);
    }

    public List<Group> findAllByChurchIdAndLeaderIdNot(Long churchId, Long memberLeaderId) {
        return groupRepository.findAllByChurchIdAndLeaderMemberIdNot(churchId, memberLeaderId);
    }

    public List<Group> findJoinedGroupsByMemberId(Long memberId) {
        return groupRepository.findJoinedGroupsByMemberId(memberId);
    }

    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    public void editGroup(long id, GroupForm groupForm) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid group id.");
        }
        if (groupForm == null) {
            throw new IllegalArgumentException("Group form data is required.");
        }

        groupRepository.editGroupFromGroupForm(id, groupForm);
        groupRepository.removeAllLeadersFromGroup(id);
        assignLeadersToGroup(groupForm.getLeaderIds(), id);
        assignCategoriesToGroup(groupForm.getCategoryIds(), id);
    }

    public GroupForm groupToGroupForm(Group group) {
        List<Long> leaderIds = group.getLeaders() == null
                ? null
                : group.getLeaders().stream().map(Member::getId).collect(Collectors.toList());

        List<Long> categoryIds = group.getCategories() == null
                ? null
                : group.getCategories().stream().map(Category::getId).collect(Collectors.toList());

        return GroupForm.builder()
                .id(group.getId())
                .title(group.getTitle())
                .description(group.getDescription())
                .schedule(group.getSchedule())
                .frequency(group.getFrequency())
                .location(group.getLocation())
                .address(group.getAddress())
                .leaderIds(leaderIds)
                .categoryIds(categoryIds)
                .gender(group.getGender())
                .ages(group.getAges())
                .churchId(group.getChurchId())
                .build();
    }

    private void assignLeadersToGroup(List<Long> leaderIds, Long groupId) {
        if (groupId == null || groupId <= 0) {
            throw new IllegalArgumentException("Invalid group id for leader assignment.");
        }
        if (leaderIds == null || leaderIds.isEmpty()) {
            throw new IllegalArgumentException("At least one leader must be selected.");
        }

        for (Long leaderId : leaderIds) {
            if (leaderId == null || leaderId <= 0) {
                throw new IllegalArgumentException("One or more selected leaders are invalid.");
            }
            groupRepository.assignLeaderToGroup(groupId, leaderId);
        }
    }

    private void assignCategoriesToGroup(List<Long> categoryIds, Long groupId) {
        if (groupId == null || groupId <= 0) {
            throw new IllegalArgumentException("Invalid group id for category assignment.");
        }
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new IllegalArgumentException("At least one category must be selected.");
        }

        for (Long categoryId : categoryIds) {
            if (categoryId == null || categoryId <= 0) {
                throw new IllegalArgumentException("One or more selected leaders are invalid.");
            }
            groupRepository.assignCategoryToGroup(groupId, categoryId);
        }
    }

}
