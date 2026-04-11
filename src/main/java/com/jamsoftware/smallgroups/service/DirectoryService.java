package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.GroupCard;
import com.jamsoftware.smallgroups.repository.DirectoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectoryService {
    DirectoryRepository directoryRepository;

     public DirectoryService(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    public List<GroupCard> findAll() {
        return directoryRepository.findAll();
    }

//    public GroupCard findById(long id) {
//        Optional<GroupCard> groupCard = directoryRepository.findById(id);
//        return groupCard.orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
//    }

    public long insert(GroupCard group) {
        return directoryRepository.insert(group);
    }

    public void update(GroupCard group) {
        directoryRepository.update(group);
    }

    public void deleteGroupById(long id) {
        directoryRepository.deleteGroupById(id);
    }

}
