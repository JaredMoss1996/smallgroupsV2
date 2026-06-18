package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.Group;
import com.jamsoftware.smallgroups.model.GroupForm;
import com.jamsoftware.smallgroups.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
         long groupId = groupRepository.createGroupFromGroupForm(groupForm);
         for (Long leaderId : groupForm.getLeaderIds()) {
             groupRepository.assignLeaderToGroup(groupId, leaderId);
         }
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

    public int editGroup(long id, Group group) {
        return groupRepository.editGroup(id, group);
    }

}
