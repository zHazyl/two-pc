package com.tnh.twophasecommit.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tnh.twophasecommit.domain.Payment;
import com.tnh.twophasecommit.service.CoordinatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
public class CoordinatorServiceImpl implements CoordinatorService {

    private static boolean debug = false;

    @Override
    public Payment pay(Payment payment) throws IOException, InterruptedException, ExecutionException {
        Map<String, String> data = new HashMap<>();
        data.put("id", payment.getId().toString());
        data.put("uid", payment.getUid().toString());
        data.put("total", payment.getTotal().toString());

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(data);
        /*
        "{"id":"1", "uid":"1", "total":"20"}"
        * */

        ExecutorService threadpool = Executors.newCachedThreadPool();

        Future<Boolean> votePayment = threadpool.submit(() -> votePayment(requestBody));
//        var votePayment = votePayment(requestBody); // done
//        var voteUser = voteUser(requestBody); // done

        Future<Boolean> voteUser = threadpool.submit(() -> voteUser(requestBody));

        while (!(votePayment.isDone() && voteUser.isDone()) || debug == true) {
            String isDebug = "";
            if (debug) {
                isDebug = "Debug: ";
            }
            log.info(isDebug + "voting");
        }
        log.info(votePayment.get().toString());
        log.info(voteUser.get().toString());

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        if (votePayment.get() && voteUser.get()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/payment/commit/" + payment.getId()))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8082/user/commit/" + payment.getId()))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());
            httpClient.send(
                    request1,
                    HttpResponse.BodyHandlers.ofString());
            return payment;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/payment/rollback/" + payment.getId()))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/user/rollback/" + payment.getId()))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString());
        httpClient.send(
                request1,
                HttpResponse.BodyHandlers.ofString());
        return null;
    }

    private boolean votePayment(String data) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/payment/phase1"))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        log.info(data);
        log.info(response.body());
        return Boolean.parseBoolean(response.body());
    }

    private boolean voteUser(String data) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/user/phase1"))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        log.info(data);
        log.info(response.body());

        return Boolean.parseBoolean(response.body());
    }

    public void debug(boolean isDebug) {
        debug = isDebug;
        log.info(String.valueOf(debug));
    }
}
