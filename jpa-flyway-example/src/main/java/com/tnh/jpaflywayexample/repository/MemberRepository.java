package com.tnh.jpaflywayexample.repository;

import com.tnh.jpaflywayexample.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// Handle behavior in database
public interface MemberRepository extends JpaRepository<Member, Long> {

}
