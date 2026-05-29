package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.repository.AuthorizationRepository;
import com.jamsoftware.smallgroups.repository.GroupRepository;
import com.jamsoftware.smallgroups.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;
    private final CurrentMemberService currentMemberService;
    private final UserRepository userRepository;
    private final MemberService memberService;
    private final GroupRepository groupRepository;

    AuthorizationService(AuthorizationRepository authorizationRepository,
                         CurrentMemberService currentMemberService,
                         UserRepository userRepository,
                         MemberService memberService,
                         GroupRepository groupRepository) {
        this.authorizationRepository = authorizationRepository;
        this.currentMemberService = currentMemberService;
        this.userRepository = userRepository;
        this.memberService = memberService;
        this.groupRepository = groupRepository;
    }

    public boolean canEditGroup(Long groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        if (userRepository.getUserRole(currentMemberService.getCurrentMember().getAppUserId())
                .orElseThrow(() -> new RuntimeException("Role not found for user id: " + currentMemberService.getCurrentMember().getAppUserId()))
                .equals("CHURCH_ADMIN")) {
            return currentMemberService.getCurrentMember().getChurchId() == groupRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Group not found for group id: " + groupId)).getChurchId();
        }
        return authorizationRepository.isGroupLeader(groupId, currentMemberService.getCurrentMember().getId());
    }

    public boolean canEditLeader(Long leaderId) {
        Member admin = currentMemberService.getCurrentMember();
        Member leader = memberService.getMemberById(leaderId)
                .orElseThrow(() -> new RuntimeException("Leader not found"));
        String leaderRole = userRepository.getUserRole(leader.getAppUserId())
                .orElseThrow(() -> new RuntimeException("Role not found for user id: " + leader.getAppUserId()));
        return admin.getChurchId() == leader.getChurchId() && leaderRole.equals("LEADER");
    }
}
