package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.Group;
import com.jamsoftware.smallgroups.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
