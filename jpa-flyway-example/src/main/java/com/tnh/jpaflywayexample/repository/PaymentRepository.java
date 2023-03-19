package com.tnh.jpaflywayexample.repository;

import com.tnh.jpaflywayexample.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
