package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.repository.AuthorizationRepository;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;
    private final CurrentMemberService currentMemberService;

    AuthorizationService(AuthorizationRepository authorizationRepository, CurrentMemberService currentMemberService) {
        this.authorizationRepository = authorizationRepository;
        this.currentMemberService = currentMemberService;
    }

    public boolean canEditGroup(Long groupId) {
        return authorizationRepository.isGroupLeader(groupId, currentMemberService.getCurrentMember().getId());
    }
}
