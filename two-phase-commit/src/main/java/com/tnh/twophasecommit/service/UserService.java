package com.tnh.twophasecommit.service;

import com.tnh.twophasecommit.domain.User;
import com.tnh.twophasecommit.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private static Map<Long, Integer> pays = new HashMap<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean pay(Long id,BigDecimal total, Long payId) {
        pays.put(payId, -1);
        ExecutorService threadpool = Executors.newCachedThreadPool();
        var user = userRepository.getById(id);
        if (user.getTotal().compareTo(total) == -1) {
            threadpool.submit(() -> {
                while (true) {
                    log.info("user: no");
                    if (pays.get(payId).equals(0)) {
                        log.info("roll back");
                        break;
                    }
                }
            });
            return false;
        }
        threadpool.submit(() -> {
            while (true) {
                log.info("user: yes");
                if (pays.get(payId).equals(0)) {
                    log.info("rollback");
                    break;
                }
                if (pays.get(payId).equals(1)) {
                    log.info("commit");
                    user.setTotal(user.getTotal().subtract(total));
                    userRepository.save(user);
                    break;
                }
            }
        });
        return true;
    }

    public void rollback(Long id) {
        log.info("rollback" + id.toString());
        pays.put(id, 0);
    }

    public void commit(Long id) {
        log.info("commit" + id.toString());
        pays.put(id, 1);
    }

    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
