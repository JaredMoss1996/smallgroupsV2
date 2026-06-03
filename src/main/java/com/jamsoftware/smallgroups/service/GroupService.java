package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.Group;
import com.jamsoftware.smallgroups.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    GroupRepository groupRepository;

     public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
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
