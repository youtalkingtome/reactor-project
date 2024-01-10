package org.example;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class NestedAsyncExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Mono<String>> futureMono1 = fetchDataAsync1();
        CompletableFuture<Mono<String>> futureMono2 = fetchDataAsync2();

        CompletableFuture<String> future1 = futureMono1.thenCompose(Mono::toFuture);
        CompletableFuture<String> future2 = futureMono2.thenCompose(Mono::toFuture);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2);
        combinedFuture.join(); // Wait for all to complete

        String result1 = future1.get();
        String result2 = future2.get();

        System.out.println("Result 1: " + result1);
        System.out.println("Result 2: " + result2);
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
