package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.CustomUserDetails;
import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.repository.MemberRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class CurrentMemberService {
    private final MemberRepository memberRepository;
    private Member cachedMember;

    public CurrentMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public CustomUserDetails getUser() {
        return (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public Member getCurrentMember() {
        if (cachedMember == null) {
            Long userId = getUser().getUserId();
            cachedMember = memberRepository.getMemberByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Member not found"));
        }
        return cachedMember;
    }
}
