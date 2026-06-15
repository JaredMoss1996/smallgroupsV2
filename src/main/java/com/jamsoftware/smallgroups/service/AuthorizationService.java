package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.Group;
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

    //returns true if
    //member is a super admin or
    //member is a church admin and the group is in the same church as the admin or
    //member is a leader of the group
    public boolean canEditGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        Member currentMember = currentMemberService.getCurrentMember();
        String currentUserRole = userRepository.getUserRole(currentMember.getAppUserId())
                .orElseThrow(() -> new RuntimeException("Role not found for user id: " + currentMember.getAppUserId()));

        if (currentUserRole.equals("SUPER_ADMIN")) {
            return true;
        }

        if (currentUserRole.equals("CHURCH_ADMIN")) {
            return currentMemberService.getCurrentMember().getChurchId() == group.getChurchId();
        }
        return authorizationRepository.isGroupLeader(groupId, currentMemberService.getCurrentMember().getId());
    }

    //returns true if
    //the user is a super admin or
    //the user is a church admin and the leader is a member of the same church as the admin and has the role of leader
    public boolean canEditLeader(Long leaderId) {
        Member currentMember = currentMemberService.getCurrentMember();
        String currentMemberRole = userRepository.getUserRole(currentMember.getAppUserId())
                .orElseThrow(() -> new RuntimeException("Role not found for user id: " + currentMember.getAppUserId()));
        if (currentMemberRole.equals("SUPER_ADMIN")) {
            return true;
        }
        Member leader = memberService.getMemberById(leaderId)
                .orElseThrow(() -> new RuntimeException("Leader not found"));
        String leaderRole = userRepository.getUserRole(leader.getAppUserId())
                .orElseThrow(() -> new RuntimeException("Role not found for user id: " + leader.getAppUserId()));
        return currentMember.getChurchId() == leader.getChurchId() && leaderRole.equals("LEADER");
    }
}
