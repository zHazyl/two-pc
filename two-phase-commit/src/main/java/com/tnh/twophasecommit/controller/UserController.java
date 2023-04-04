package com.tnh.twophasecommit.controller;

import com.tnh.twophasecommit.domain.Payment;
import com.tnh.twophasecommit.domain.User;
import com.tnh.twophasecommit.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/phase1")
    public boolean phase1(@RequestBody Payment payment) {
        return userService.pay(payment.getUid(), payment.getTotal(), payment.getId());
    }

    @PostMapping("/rollback/{id}")
    public void rollback(@PathVariable Long id) {
        userService.rollback(id);
    }

    @PostMapping("/commit/{id}")
    public void commit(@PathVariable Long id) {
        userService.commit(id);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

}
