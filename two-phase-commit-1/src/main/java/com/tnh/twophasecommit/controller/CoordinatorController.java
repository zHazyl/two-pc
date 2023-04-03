package com.tnh.twophasecommit.controller;

import com.tnh.twophasecommit.domain.Payment;
import com.tnh.twophasecommit.service.CoordinatorService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/pay")
public class CoordinatorController {
    private final CoordinatorService coordinatorService;
    public CoordinatorController(CoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }
    @PostMapping
    public Payment pay(@RequestBody Payment payment) throws IOException, InterruptedException, ExecutionException {
        return coordinatorService.pay(payment);
    }

    @PostMapping("/debug/{isDebug}")
    public void debug(@PathVariable boolean isDebug) {
        coordinatorService.debug(isDebug);
    }
}
