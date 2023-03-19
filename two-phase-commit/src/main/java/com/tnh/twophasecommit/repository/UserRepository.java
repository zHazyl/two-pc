package com.tnh.twophasecommit.repository;

import com.tnh.twophasecommit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
