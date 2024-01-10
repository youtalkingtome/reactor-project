package org.example;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class IndependentAsyncExecution {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime1 = System.currentTimeMillis();
        CompletableFuture<Mono<String>> futureMono1 = fetchDataAsync1();
        String result1 = futureMono1.thenCompose(Mono::toFuture).get();
        long endTime1 = System.currentTimeMillis();

        long startTime2 = System.currentTimeMillis();
        CompletableFuture<Mono<String>> futureMono2 = fetchDataAsync2();
        String result2 = futureMono2.thenCompose(Mono::toFuture).get();
        long endTime2 = System.currentTimeMillis();

        System.out.println("Result 1: " + result1);
        System.out.println("Time taken for API 1: " + (endTime1 - startTime1) + " ms");

        System.out.println("Result 2: " + result2);
        System.out.println("Time taken for API 2: " + (endTime2 - startTime2) + " ms");
    }

    private static CompletableFuture<Mono<String>> fetchDataAsync1() {
        // Simulate an API returning CompletableFuture<Mono<String>>
        return CompletableFuture.supplyAsync(() -> Mono.just("Data 1").delayElement(Duration.ofSeconds(2)));
    }

    private static CompletableFuture<Mono<String>> fetchDataAsync2() {
        // Simulate an API returning CompletableFuture<Mono<String>>
        return CompletableFuture.supplyAsync(() -> Mono.just("Data 2").delayElement(Duration.ofSeconds(3)));
    }
}
