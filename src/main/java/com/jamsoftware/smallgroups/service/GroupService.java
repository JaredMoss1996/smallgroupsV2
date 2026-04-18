package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.GroupCard;
import com.jamsoftware.smallgroups.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    GroupRepository groupRepository;

     public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<GroupCard> findAll() {
        return groupRepository.findAll();
    }

    public List<GroupCard> findAllByLeaderId(Long memberLeaderId) {
         return groupRepository.findAllByLeaderMemberId(memberLeaderId);
    }

//    public GroupCard findById(long id) {
//        Optional<GroupCard> groupCard = directoryRepository.findById(id);
//        return groupCard.orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
//    }

//    public long insert(GroupCard group) {
//        return groupRepository.insert(group);
//    }
//
//    public void update(GroupCard group) {
//        groupRepository.update(group);
//    }
//
//    public void deleteGroupById(long id) {
//        groupRepository.deleteGroupById(id);
//    }

}
