package com.tnh.jpaflywayexample.controller;

import com.tnh.jpaflywayexample.domain.Member;
import com.tnh.jpaflywayexample.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Build API
@RestController
@RequestMapping("/api/v1/members")
// example : http://localhost:8080/api/v1/members is listened by this controller
public class MemberController {

    // Dependency injection - should read this
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // with Get verb call this method to get all members
    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    // with Post verb this method to add new member
    @PostMapping
    public Member addMember(@RequestBody Member member) {
        return memberService.addMember(member);
    }

}
