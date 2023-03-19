package com.tnh.twophasecommit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tnh.twophasecommit.domain.Payment;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface CoordinatorService {
    Payment pay(Payment payment) throws IOException, InterruptedException, ExecutionException;
}
