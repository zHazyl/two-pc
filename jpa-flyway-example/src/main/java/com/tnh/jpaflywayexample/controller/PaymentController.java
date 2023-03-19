package com.tnh.jpaflywayexample.controller;

import com.tnh.jpaflywayexample.domain.Payment;
import com.tnh.jpaflywayexample.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/phase1")
    public boolean phase1(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @PostMapping("/rollback/{id}")
    public void rollback(@PathVariable Long id) {
        paymentService.rollback(id);
    }

    @PostMapping("/commit/{id}")
    public void commit(@PathVariable Long id) {
        paymentService.commit(id);
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }

}
