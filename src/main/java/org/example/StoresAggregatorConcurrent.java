package org.example;

import reactor.core.publisher.Mono;
import java.time.Duration;
import java.time.Instant;

public class StoresAggregatorConcurrent {

    public static void main(String[] args) {
        Instant start = Instant.now();
        Mono<String> indianStores = getIndianStores();
        Mono<String> singaporeStores = getSingaporeStores();

        // Using Mono.zip to combine the results of both Monos
        Mono.zip(indianStores, singaporeStores)
                .doOnSuccess(tuple -> {
                    String result1 = tuple.getT1();
                    String result2 = tuple.getT2();
                    System.out.println("Result from API 1: " + result1);
                    System.out.println("Result from API 2: " + result2);
                })
                .block(); // Only block at the end
        Instant end = Instant.now();
        System.out.println("Time taken for Main: " + Duration.between(start, end).toMillis() + " ms");
    }

    private static Mono<String> getIndianStores() {
        Instant start = Instant.now();
        Mono<String> result = Mono.just("Response from API 1")
                .delayElement(Duration.ofSeconds(3)); // Simulates a delay
        Instant end = Instant.now();
        System.out.println("Time taken for Indian Stores: " + Duration.between(start, end).toMillis() + " ms");
        return result;
    }

    private static Mono<String> getSingaporeStores() {
        Instant start = Instant.now();
        Mono<String> result= Mono.just("Response from API 2")
                .delayElement(Duration.ofSeconds(4)); // Simulates a delay
        Instant end = Instant.now();
        System.out.println("Time taken for Singapore Stores: " + Duration.between(start, end).toMillis() + " ms");
        return result;
    }
}
