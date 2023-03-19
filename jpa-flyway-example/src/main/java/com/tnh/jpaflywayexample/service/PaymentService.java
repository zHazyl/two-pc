package com.tnh.jpaflywayexample.service;

import com.tnh.jpaflywayexample.domain.Payment;

public interface PaymentService {

    boolean createPayment(Payment payment);
    void rollback(Long id);
    void commit(Long id);
    Payment getPayment(Long id);

}