package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.repository.SecurityRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationService {

    private final SecurityRepository securityRepository;
    private final CurrentMemberService currentMemberService;

    AuthorizationService(SecurityRepository securityRepository, CurrentMemberService currentMemberService) {
        this.securityRepository = securityRepository;
        this.currentMemberService = currentMemberService;
    }

    public boolean canEditGroup(Long groupId) {
        return securityRepository.isGroupLeader(groupId, currentMemberService.getCurrentMember().getId());
    }
}
