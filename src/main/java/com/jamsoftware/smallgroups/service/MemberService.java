package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.Member;
import com.jamsoftware.smallgroups.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getLeadersByChurchId(long churchId) {
        return memberRepository.getLeadersByChurchId(churchId);
    }

    public Optional<Member> getMemberById(long memberId) {
        return memberRepository.getMemberById(memberId);
    }
}
