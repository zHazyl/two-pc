package com.tnh.jpaflywayexample.service.impl;

import com.tnh.jpaflywayexample.domain.Member;
import com.tnh.jpaflywayexample.repository.MemberRepository;
import com.tnh.jpaflywayexample.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    // Dependency injection - should read this
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
