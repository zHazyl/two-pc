package com.tnh.jpaflywayexample.service;

import com.tnh.jpaflywayexample.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;

// Fulfill logic
public interface MemberService {

    Member addMember(Member member);
    List<Member> getAllMembers();

}
