package com.tnh.jpaflywayexample.service.impl;

import com.tnh.jpaflywayexample.domain.Payment;
import com.tnh.jpaflywayexample.repository.PaymentRepository;
import com.tnh.jpaflywayexample.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class PaymentImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private static Map<Long, Integer> pays = new HashMap<>();
    // "id": -1, 0, 1

    public PaymentImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public boolean createPayment(Payment payment) {
        pays.put(payment.getId(), -1);

        ExecutorService threadpool = Executors.newCachedThreadPool();
        log.info(payment.toString());
        if (paymentRepository.existsById(payment.getId())) {
            threadpool.submit(() -> {
                while (true) {
                    log.info("payment: no");
                    if (pays.get(payment.getId()).equals(0)) {
                        log.info("rollback");
                        //rollback
                        break;
                    }
                }
            });
            return false;
        }
        threadpool.submit(() -> {
            while (true) {
                log.info("payment: yes");
                if (pays.get(payment.getId()).equals(0)) {
                    log.info("rollback");
                    break;
                }
                if (pays.get(payment.getId()).equals(1)) {
                    log.info("commit");
                    //commit
                    paymentRepository.save(payment);
                    break;
                }
            }
        });
        return true;
    }

    @Override
    public void rollback(Long id) {
        log.info("rollback" + id.toString());
        pays.put(id, 0);
    }

    @Override
    public void commit(Long id) {
        log.info("commit" + id.toString());
        pays.put(id, 1);
    }

    @Override
    public Payment getPayment(Long id) {
        return paymentRepository.getById(id);
    }
}
